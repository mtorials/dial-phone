package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.exceptions.SyncException
import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

class Synchronizer(
    private val listeners: MutableList<Listener>,
    private val phone: DialPhoneApi,
    private val client: HttpClient,
    private val fullState: Boolean = false,
    private val initCallback: suspend (DialPhoneApi) -> Unit,
) {


    val joinedRoomIds: List<String>
        get() = joined.toList()
    val invitedRoomIds: List<String>
        get() = invited.toList()

    private val joined: MutableList<String> = mutableListOf()
    private val invited: MutableList<String> = mutableListOf()

    private var lastTimeBatch: String? = null
    private var initialSync: Boolean = true
    private var stopFlag: Boolean = false

    init {
        // Cache Listeners
        // listeners.add(UserCacheListener(phone.cache))
    }

    fun addListener(listener: Listener) = listeners.add(listener)

    fun stop() {
        this.stopFlag = true
    }

    // TODO remove global scope
    // TODO check if room duplicates are added
    suspend fun sync() {
        while(true) {
            try {
                val res : SyncResponse = getSyncResponse()
                lastTimeBatch = res.nextBatch
                res.roomSync?.join?.forEach { (roomID, roomEvents) ->
                    joined.add(roomID)
                    roomEvents.timeline.events.forEach { event ->
                        listeners.forEach {
                            it.onRoomEvent(event, roomID, phone, initialSync)
                        }
                    }
                }
                res.roomSync?.invite?.forEach { (roomID, roomEvents) ->
                    invited.add(roomID)
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach {
                            it.onRoomEvent(event, roomID, phone, initialSync)
                        }
                    }
                }
                if (initialSync) GlobalScope.launch { initCallback(phone) }
                initialSync = false
                //println(lastTimeBatch)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
            if (stopFlag) {
                stopFlag = false
                break
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