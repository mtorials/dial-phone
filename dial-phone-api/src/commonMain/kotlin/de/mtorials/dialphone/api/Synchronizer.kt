package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.exceptions.SyncException
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
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
) {

    private val listeners: MutableList<GenericListener<DialPhoneApi>> = mutableListOf()

    val joinedRoomIds: List<String>
        get() = joined.toList()
    val invitedRoomIds: List<String>
        get() = invited.toList()

    private val joined: MutableList<String> = mutableListOf()
    private val invited: MutableList<String> = mutableListOf()

    private var lastTimeBatch: String? = null
    private var initialSync: Boolean = true

    fun addListener(listener: GenericListener<DialPhoneApi>) = listeners.add(listener)

    // TODO add knocked and left
    fun sync(
        coroutineScope: CoroutineScope
    ) = coroutineScope.launch {
        while(isActive) {
            try {
                val res : SyncResponse = getSyncResponse()
                lastTimeBatch = res.nextBatch
                // Joined
                joined.clear()
                res.roomSync?.join?.forEach { (roomID, roomEvents) ->
                    joined.add(roomID)
                    roomEvents.timeline.events.forEach { e ->
                        val event = roomEventHook?.manipulateEvent(e) ?: e
                        listeners.forEach {
                            launch { it.onRoomEvent(event, roomID, phone, initialSync) }
                        }
                    }
                }
                // Invited
                invited.clear()
                res.roomSync?.invite?.forEach { (roomID, roomEvents) ->
                    invited.add(roomID)
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach {
                            launch { it.onRoomEvent(event, roomID, phone, initialSync) }
                        }
                    }
                }
                if (initialSync) initCallback(phone)
                initialSync = false
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