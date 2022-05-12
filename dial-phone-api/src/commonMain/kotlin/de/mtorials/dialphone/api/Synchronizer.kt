package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.exceptions.SyncException
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MatrixRoomEvent
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

class Synchronizer(
    private val phone: DialPhoneApiImpl,
    private val client: HttpClient,
    private val fullState: Boolean = false,
    private val initCallback: suspend (DialPhoneApi) -> Unit,
    private val syncCallback: (SyncResponse) -> Unit = {},
) {

    var beforeRoomEvent: (MatrixEvent) -> MatrixEvent = { it }

    private val listeners: MutableList<GenericListener<DialPhoneApi>> = mutableListOf()

    private var lastTimeBatch: String? = null
    private var initialSync: Boolean = true

    fun addListener(listener: GenericListener<DialPhoneApi>) = listeners.add(listener)

    // TODO add knocked and left
    // TODO tidy up
    fun sync(
        coroutineScope: CoroutineScope
    ) = coroutineScope.launch {
        while(isActive) {
            val res : SyncResponse = getSyncResponse()
            lastTimeBatch = res.nextBatch
            // Joined
            res.rooms?.join?.forEach { (roomID, roomEvents) ->
                roomEvents.timeline.events.forEach { toRoomListeners(it, roomID) }
                roomEvents.state?.events?.forEach { toRoomListeners(it, roomID) }
            }
            // Invited
//            res.rooms?.invite?.forEach { (roomID, roomEvents) ->
//                roomEvents.inviteState.events.forEach { toRoomListeners(it, roomID) }
//            }
            // To device
//            res.toDevice?.events?.forEach { toDeviceListeners(it) }
            // Presence
//            res.presence?.events?.forEach { toPresenceListener(it) }
            if (initialSync) initCallback(phone)
            initialSync = false
            syncCallback(res)
        }
    }

    private fun CoroutineScope.toRoomListeners(event: JsonObject, roomId: String) {
        listeners.forEach {
            launch {
                try {
                    val e: MatrixEvent = phone.format.decodeFromJsonElement(event)
                    val eventAfter = beforeRoomEvent(e)
                    it.onRoomEvent(eventAfter, RoomId(roomId), phone, initialSync)
                } catch (e: RuntimeException) {
                    e.printStackTrace()
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
                } catch (e: RuntimeException) {
                    e.printStackTrace()
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
                catch (e: RuntimeException) { e.printStackTrace() }
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
            }
//            println(str)
//            return TODO()
        } catch (mappingException: SerializationException) {
            throw SyncException(mappingException, "Deserialization Exception while Syncing")
        } catch (e: Exception) {
            throw SyncException(e, "Exception while syncing")
        }
    }
}