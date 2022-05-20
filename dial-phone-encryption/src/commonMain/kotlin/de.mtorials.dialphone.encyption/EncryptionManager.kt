package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.requests.encryption.*
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.api.ids.roomId
import de.mtorials.dialphone.api.ids.userId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.requests.SendToDeviceRequest
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.olmmachine.bindings.DeviceLists
import de.mtorials.dialphone.olmmachine.bindings.OlmMachineInterface
import de.mtorials.dialphone.olmmachine.bindings.Request
import de.mtorials.dialphone.olmmachine.bindings.RequestType
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.*
import kotlinx.serialization.json.*

// TODO abstract the account management
// TODO remember account
class EncryptionManager(
    // TODO store has no purpose
    private val machine: OlmMachineInterface,
    private val client: E2EEClient,
    private val deviceId: String,
    private val phone: DialPhone,
    private val json: Json,
) {

    private val sessionLock = Mutex()

    fun decryptEvent(roomId: RoomId, event: MatrixEvent) : MatrixEvent {
        return machine.decryptRoomEvent(json.encodeToString(event), roomId.toString()).run { json.decodeFromString(clearEvent) }
    }

    suspend fun encryptEvent(room: JoinedRoom, type: String, content: EventContent) : MRoomEncrypted.MRoomEncryptedContent {
        var request: Request? = null
        // TODO lock call
        request = machine.getMissingSessions(room.members.map { it.id.toString() })
        if (request != null) handleRequest(request)
        machine.shareRoomKey(room.id.toString(), room.members.map { it.id.toString() }).forEach { request ->
            handleRequest(request)
        }
        return machine.encrypt(room.id.toString(), type, json.encodeToString(content))
//            .also { println(it) }
            .run { json.decodeFromString(this) }
    }

    fun handleEvent(events: List<JsonElement>, deviceChanges: SyncResponse.DevicesList?, keyCounts: Map<String, Int>) {
        machine.receiveSyncChanges(
            events = json.encodeToString(events),
            deviceChanges = deviceChanges.run {
                DeviceLists(this?.changed ?: emptyList(), this?.left ?: emptyList()) },
            keyCounts = keyCounts,
            // TODO check if this should be null
            unusedFallbackKeys = null,
        )
    }

    suspend fun update() = machine.outgoingRequests().forEach { handleRequest(it) }

    suspend fun handleRequest(request: Request) {
        // TODO impl all
        when (request) {
            is Request.KeysUpload -> client.uploadKeys(request.body)
                .apply { machine.markRequestAsSent(request.requestId, RequestType.KEYS_UPLOAD, json.encodeToString(this)) }
            is Request.KeysClaim -> client.claimKeys(KeyClaimRequest(request.oneTimeKeys.map { it.key.userId() to it.value }.toMap()))
                .apply { machine.markRequestAsSent(request.requestId, RequestType.KEYS_CLAIM, json.encodeToString(this)) }
            is Request.KeysQuery -> client.queryKeys(KeyQueryRequest(request.users.associate { it.userId() to emptyList() }))
                .apply { machine.markRequestAsSent(request.requestId, RequestType.KEYS_QUERY, json.encodeToString(this)) }
            is Request.ToDevice -> client.sendEventToDevice(request.eventType, SendToDeviceRequest(
                json.decodeFromString(request.body)
            ))
                // TODO check if this response is ok
                .apply { machine.markRequestAsSent(request.requestId, RequestType.TO_DEVICE, "{}") }
            is Request.RoomMessage -> phone.apiRequests.sendMessageEvent(request.eventType, request.content, request.roomId.roomId())
        }
    }
}