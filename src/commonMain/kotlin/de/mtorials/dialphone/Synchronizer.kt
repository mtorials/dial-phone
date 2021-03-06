package de.mtorials.dialphone

import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.exceptions.SyncException
import de.mtorials.dialphone.listener.Listener
import de.mtorials.dialphone.listener.UserCacheListener
import de.mtorials.dialphone.responses.SyncResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.cio.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

class Synchronizer(
    private val listeners: MutableList<Listener>,
    private val phone: DialPhoneImpl,
    private val client: HttpClient,
    private val fullState: Boolean = false,
    private val initCallback: suspend (DialPhone) -> Unit,
) {

    private var lastTimeBatch: String? = null
    private var initialSync: Boolean = true
    private var stopFlag: Boolean = false

    init {
        // Chache Listeners
        listeners.add(UserCacheListener(phone.cache))
    }

    fun addListener(listener: Listener) = listeners.add(listener)

    fun stop() {
        this.stopFlag = true
    }

    suspend fun sync() {
        while(true) {
            val joinedRooms: MutableList<RoomFuture> = mutableListOf()
            val invitedRooms: MutableList<InvitedRoomActions> = mutableListOf()
            try {
                val res : SyncResponse = getSyncResponse()
                lastTimeBatch = res.nextBatch
                res.roomSync.join.forEach { (roomID, roomEvents) ->
                    joinedRooms.add(RoomFutureImpl(roomID, phone))
                    roomEvents.timeline.events.forEach { event ->
                        listeners.forEach {
                            if (initialSync) it.onOldRoomEvent(event, roomID, phone)
                            else it.onNewRoomEvent(event, roomID, phone)
                        }
                    }
                }
                res.roomSync.invite.forEach { (roomID, roomEvents) ->
                    invitedRooms.add(InvitedRoomActionsImpl(phone, roomID))
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach {
                            if (initialSync) it.onOldRoomEvent(event, roomID, phone)
                            else it.onNewRoomEvent(event, roomID, phone)
                        }
                    }
                }
                // Only first sync is initial
                phone.cache.joinedRooms = joinedRooms
                phone.cache.invitedRooms = invitedRooms
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
                url(phone.homeserverUrl + DialPhone.MATRIX_PATH + "sync")
                method = HttpMethod.Get
                header("Authorization", "Bearer ${phone.token}")
                header("Content-Type", "application/json")
                parameter("timeout", DialPhone.TIMEOUT)
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