package de.mtorials.dialphone

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.Listener
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.responses.SyncResponse
import kotlinx.coroutines.runBlocking
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import kotlin.reflect.KClass

class Synchronizer(
    private val listeners: MutableList<Listener>,
    private val phone: DialPhoneImpl,
    subTypes: Array<KClass<out MatrixEvent>>,
    private val fullState: Boolean = false
) {

    private val mapper = jacksonObjectMapper()
    private var lastTimeBatch: String? = null
    private val client = OkHttp()

    init {
        subTypes.forEach { mapper.registerSubtypes(it.java) }
        runBlocking {
            try {
                val res = getSyncResponse()
                lastTimeBatch = res.nextBatch
                res.roomSync.join.forEach { (roomID, roomEvents) ->
                    phone.cache.joinedRooms.add(RoomFutureImpl(roomID, phone))
                    roomEvents.timeline.events.forEach { event ->
                        listeners.forEach { it.onOldRoomEvent(event, roomID, phone) }
                    }
                }
                res.roomSync.invite.forEach { (roomID, roomEvents) ->
                    phone.cache.invitedRooms.add(InvitedRoomActionsImpl(phone, roomID))
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach { it.onOldRoomEvent(event, roomID, phone) }
                    }
                }
            } catch (e: UnrecognizedPropertyException) {
                e.printStackTrace()
            }
        }
    }

    fun addListener(listener: Listener) = listeners.add(listener)

    suspend fun sync() {
        while(true) {
            val joinedRooms: MutableList<RoomFuture> = mutableListOf()
            val invitedRooms: MutableList<InvitedRoomActions> = mutableListOf()
            try {
                //println("again!")
                val res : SyncResponse = getSyncResponse()
                lastTimeBatch = res.nextBatch
                res.roomSync.join.forEach { (roomID, roomEvents) ->
                    joinedRooms.add(RoomFutureImpl(roomID, phone))
                    roomEvents.timeline.events.forEach { event ->
                        listeners.forEach { it.onNewRoomEvent(event, roomID, phone) }
                    }
                }
                res.roomSync.invite.forEach { (roomID, roomEvents) ->
                    invitedRooms.add(InvitedRoomActionsImpl(phone, roomID))
                    roomEvents.inviteState.events.forEach { event ->
                        listeners.forEach { it.onNewRoomEvent(event, roomID, phone) }
                    }
                }
                phone.cache.joinedRooms = joinedRooms
                phone.cache.invitedRooms = invitedRooms
                //println(lastTimeBatch)
            } catch (e: UnrecognizedPropertyException) {
                e.printStackTrace()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
            //delay(10000)
        }
    }

    private suspend fun getSyncResponse() : SyncResponse {
        var request = Request(Method.GET, phone.homeserverUrl + DialPhone.MATRIX_PATH + "sync")
            .query("timeout", DialPhone.TIMEOUT)
            .query("full_state", fullState.toString())
            .header("Authorization", "Bearer ${phone.token}")
        if (lastTimeBatch != null) request = request.query("since", lastTimeBatch.toString())
        val string = client(request).bodyString()
        if (string.isEmpty()) throw RuntimeException("Response is empty.")
        //println(string)
        return mapper.readValue(string)
    }
}