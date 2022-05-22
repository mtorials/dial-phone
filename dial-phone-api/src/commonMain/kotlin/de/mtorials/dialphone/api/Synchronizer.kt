package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.exceptions.SyncException
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.roomId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

class Synchronizer(
    private val phone: DialPhoneApiImpl,
    private val client: HttpClient,
    private val fullState: Boolean = false,
    private val initCallback: suspend (DialPhoneApi) -> Unit,
    private val syncCallback: (SyncResponse) -> Unit = {},
    private val logLevel: DialPhoneLogLevel,
) {

    var beforeRoomEvent: suspend (RoomId, MatrixEvent) -> MatrixEvent = { _, e -> e }

    private val listeners: MutableList<GenericListener<DialPhoneApi>> = mutableListOf()

    private var lastTimeBatch: String? = null
    private var initialSync: Boolean = true

    fun addListener(listener: GenericListener<DialPhoneApi>) = listeners.add(listener)

    // TODO add knocked and left (banned?)
    // TODO tidy up
    fun sync(
        coroutineScope: CoroutineScope,
        once: Boolean = false,
    ) = coroutineScope.launch {
        do {
            val res : SyncResponse = getSyncResponse()
            if (logLevel.sync.message) println("[Synchronizer] Got sync response with next batch ${res.nextBatch}.")
            lastTimeBatch = res.nextBatch
            listeners.forEach { it.onSyncResponse(res, coroutineScope) }
            // Joined
            res.rooms?.join?.forEach { (roomID, roomEvents) ->
                roomEvents.timeline.events.forEach {
                    toListener(it, roomID) { e, id -> onJoinedRoomTimelineEvent(e, id, phone, initialSync) }
                }
                roomEvents.state?.events?.forEach {
                    toListener(it, roomID) { e, id -> onJoinedRoomStateEvent(e as MatrixStateEvent, id, phone, initialSync) }
                }
                roomEvents.ephemeral?.events?.forEach {
                    toListener(it, roomID) { e, id -> onJoinedRoomEphemeralEvent(e, id, phone, initialSync) }
                }
            }
            // Invited
            res.rooms?.invite?.forEach { (roomID, roomEvents) ->
                roomEvents.inviteState.events.forEach {
                    toListener(it, roomID) { e, id -> onInvitedRoomStateEvent(e as MatrixStateEvent, id, phone, initialSync) }
                }
            }
            // Knocked
            res.rooms?.knock?.forEach { (roomID, roomEvents) ->
                roomEvents.knockState.events.forEach {
                    toListener(it, roomID) { e, id -> onKnockedRoomStateEvent(e as MatrixStateEvent, id, phone, initialSync) }
                }
            }
            // left
            res.rooms?.leave?.forEach { (roomID, roomEvents) ->
                roomEvents.state?.events?.forEach {
                    toListener(it, roomID) { e, id -> onLeftRoomStateEvent(e as MatrixStateEvent, id, phone, initialSync) }
                }
            }
            // To device
            res.toDevice?.events?.forEach { toDeviceListeners(it) }
            // Presence
            res.presence?.events?.forEach { toPresenceListener(it) }
            if (initialSync) initCallback(phone)
            initialSync = false
            syncCallback(res)
        } while(isActive && !once)
    }

    private fun CoroutineScope.toListener(
        event: JsonObject,
        roomId: String,
        callback: suspend GenericListener<DialPhoneApi>.(event: MatrixEvent, roomId: RoomId) -> Unit
    ) {
        listeners.forEach {
            launch {
                try {
                    val e: MatrixEvent = phone.format.decodeFromJsonElement(event)
                    val eventAfter = beforeRoomEvent(roomId.roomId(), e)
                    it.callback(eventAfter, RoomId(roomId))
                } catch (e: Exception) {
                    if (logLevel.sync.traces) e.printStackTrace()
                }
            }
        }
    }

    private fun CoroutineScope.toDeviceListeners(event: JsonObject) {
        listeners.forEach {
            launch {
                try {
                    val e: MatrixEvent = phone.format.decodeFromJsonElement(event)
                    it.onToDeviceEvent(e, phone, initialSync)
                } catch (e: Exception) {
                    if (logLevel.sync.traces) e.printStackTrace()
                }
            }
        }
    }

    private fun CoroutineScope.toPresenceListener(event: JsonObject) {
        listeners.forEach {
            launch {
                try {
                    val e : MatrixEvent = phone.format.decodeFromJsonElement(event)
                    it.onPresenceEvent(e, phone, initialSync)
                }
                catch (e: Exception) {
                    if (logLevel.sync.traces) e.printStackTrace()
                }
            }
        }
    }

    private suspend fun getSyncResponse() : SyncResponse {
        try {
//            val str: String = client.request {
            return client.request {
                url(phone.homeserverUrl + DialPhoneApi.MATRIX_PATH + "sync")
                method = HttpMethod.Get
                header("Authorization", "Bearer ${phone.token}")
                header("Content-Type", "application/json")
                parameter("timeout", DialPhoneApi.TIMEOUT)
                parameter("full_state", fullState.toString())
                if (lastTimeBatch != null) parameter("since", lastTimeBatch.toString())
                //this.run { println(this.build().toString()) }
            }.body()
//            println(str)
//            return TODO()
        } catch (mappingException: SerializationException) {
            throw SyncException(mappingException, "Deserialization Exception while Syncing")
        } catch (e: Exception) {
            throw SyncException(e, "Exception while syncing")
        }
    }
}