package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.requests.encryption.*
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.api.ids.roomId
import de.mtorials.dialphone.api.ids.userId
import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.requests.SendToDeviceRequest
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.olmmachine.bindings.DeviceLists
import de.mtorials.dialphone.olmmachine.bindings.OlmMachineInterface
import de.mtorials.dialphone.olmmachine.bindings.Request
import de.mtorials.dialphone.olmmachine.bindings.RequestType
import kotlinx.serialization.*
import kotlinx.serialization.json.*

// TODO abstract the account management
// TODO remember account
class EncryptionManager(
    // TODO store has no purpose
    private val machine: OlmMachineInterface,
    private val client: E2EEClient,
    private val deviceId: String,
    private val phone: DialPhoneImpl,
    private val json: Json,
) {

//    private val sessionLock = Mutex()

    suspend fun decryptEvent(room: JoinedRoom, event: MatrixEvent) : MatrixEvent {
        // TODO only if necessary
        establishSessions(room)
        // TODO check for the room key?
        machine.requestRoomKey(json.encodeToString(event), room.id.toString()).run {
            cancellation?.also { c -> handleRequest(c) }
            handleRequest(keyRequest)
        }
        return machine.decryptRoomEvent(json.encodeToString(event), room.id.toString())
            .run { json.decodeFromString(clearEvent) }
    }

    suspend fun encryptEvent(room: JoinedRoom, type: String, content: EventContent) : MRoomEncrypted.MRoomEncryptedContent {
        establishSessions(room)
        machine.shareRoomKey(room.id.toString(), room.members.map { it.id.toString() }).forEach {
            handleRequest(it)
        }
        return machine.encrypt(room.id.toString(), type, json.encodeToString(content))
//            .also { println(it) }
            .run { json.decodeFromString(this) }
    }

    fun handleEvents(toDevice: SyncResponse.ToDevice?, deviceChanges: SyncResponse.DevicesList?, keyCounts: Map<String, Int>) {
        toDevice?.events?.forEach {
            logCrypt("Got to-device event by user/device ${it["sender"]} of type ${it["type"]}. ")
        }
        machine.receiveSyncChanges(
            // TODO this is not an array?!? create an issue on matrirx-rust-sdk
            events = toDevice?.run { json.encodeToString(this) } ?: "{}",
            deviceChanges = deviceChanges.run {
                DeviceLists(this?.changed ?: emptyList(), this?.left ?: emptyList()) },
            keyCounts = keyCounts,
            unusedFallbackKeys = null,
        )
    }

    suspend fun update() {
        logCrypt("Making outgoing request...")
        machine.outgoingRequests().forEach { handleRequest(it) }
    }

    private suspend fun establishSessions(room: JoinedRoom) {
        machine.getMissingSessions(room.members.map { it.id.toString() })?.run {
            handleRequest(this)
        }
    }

    private suspend fun handleRequest(request: Request) {
        // TODO impl all
        when (request) {
            is Request.KeysUpload -> client.uploadKeys(request.body).apply {
                machine.markRequestAsSent(request.requestId, RequestType.KEYS_UPLOAD, json.encodeToString(this))
                logCrypt("Uploaded Keys. Now having $oneTimeKeyCount one time keys.")
            }

            is Request.KeysClaim -> client.claimKeys(
                KeyClaimRequest(request.oneTimeKeys.map { it.key.userId() to it.value }.toMap())
            ).apply {
                machine.markRequestAsSent(request.requestId, RequestType.KEYS_CLAIM, json.encodeToString(this))
                logCrypt("Claimed keys for ${oneTimeKeys.map { it.key.toString() }}")
            }

            is Request.KeysQuery -> client.queryKeys(
                KeyQueryRequest(request.users.associate { it.userId() to emptyList() })
            ).apply {
                machine.markRequestAsSent(request.requestId, RequestType.KEYS_QUERY, json.encodeToString(this))
                logCrypt("Queried keys.")
            }

            is Request.ToDevice -> client.sendEventToDevice(request.eventType, SendToDeviceRequest(
                json.decodeFromString(request.body)
            )).apply {
                machine.markRequestAsSent(request.requestId, RequestType.TO_DEVICE, "{}")
                logCrypt("Sent to-device event of type ${request.eventType} with id ${request.requestId}.")
            }

            is Request.SignatureUpload -> client.uploadKeys(request.body).apply {
                machine.markRequestAsSent(request.requestId, RequestType.SIGNATURE_UPLOAD, json.encodeToString(this))
                logCrypt("Uploaded Signature.")
            }

            is Request.RoomMessage -> phone.apiRequests.sendMessageEvent(request.eventType, request.content, request.roomId.roomId())

            is Request.KeysBackup -> TODO()
        }
    }

    private fun logCrypt(msg: String) {
        if (phone.logLevel.level >= DialPhoneLogLevel.DEBUG.level) println("[CRYPTO] $msg")
    }
}