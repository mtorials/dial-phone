package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.exceptions.SyncException
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.SerializationException

class Synchronizer(
    private val phone: DialPhoneApi,
    private val client: HttpClient,
    private val fullState: Boolean = false,
    private val initCallback: suspend (DialPhoneApi) -> Unit,
    private val roomEventHook: RoomEventHook? = null,
    private val syncCallback: (SyncResponse) -> Unit = {},
) {

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
            try {
                val res : SyncResponse = getSyncResponse()
                lastTimeBatch = res.nextBatch
                // Joined
                res.rooms?.join?.forEach { (roomID, roomEvents) ->
                    roomEvents.timeline.events.forEach { e ->
                        val event = roomEventHook?.manipulateEvent(e) ?: e
                        listeners.forEach {
                            launch { it.onRoomEvent(event, roomID, phone, initialSync) }
                        }
                    }
                    roomEvents.state?.events?.forEach { e ->
                        val event = roomEventHook?.manipulateEvent(e) ?: e
                        listeners.forEach {
                            launch { it.onRoomEvent(event, roomID, phone, initialSync) }
                        }
                    }
                }
                // Invited
                res.rooms?.invite?.forEach { (roomID, roomEvents) ->
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach {
                            launch { it.onRoomEvent(event, roomID, phone, initialSync) }
                        }
                    }
                }
                // To device
                res.toDevice?.events?.forEach { event ->
                    listeners.forEach {
                        launch { it.onToDeviceEvent(event, phone, initialSync) }
                    }
                }
                // Presence
                res.presence?.events?.forEach { event ->
                    listeners.forEach {
                        launch { it.onPresenceEvent(event, phone, initialSync) }
                    }
                }
                if (initialSync) initCallback(phone)
                initialSync = false
                syncCallback(res)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getSyncResponse() : SyncResponse {
        try {
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
        } catch (mappingException: SerializationException) {
            throw SyncException(mappingException, "Deserialization Exception while Syncing")
        } catch (e: Exception) {
            throw SyncException(e, "Exception while syncing")
        }
    }
}