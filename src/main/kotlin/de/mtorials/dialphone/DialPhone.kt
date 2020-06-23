package de.mtorials.dialphone


import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.entities.entityfutures.UserFuture
import kotlinx.coroutines.runBlocking
import de.mtorials.dialphone.listener.Listener
import de.mtorials.dialphone.mevents.MatrixEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class DialPhone(
    val token: String,
    val homeServerURL: String,
    listeners: List<Listener> = listOf(),
    val commandPrefix: String = "!",
    customEventTypes: Array<KClass<out MatrixEvent>> = arrayOf()
) {

    val ownId: String
    val requestObject = APIRequests(this, customEventTypes)

    private val syncObject = Synchronizer(listeners.toMutableList(), this, customEventTypes)

    init {
        ownId = runBlocking {
            requestObject.getMe().id
        }
    }

    fun addListener(listener: Listener) = syncObject.addListener(listener)

    suspend fun getJoinedRoomFutures() : List<RoomFutureImpl> =
        requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhone) }

    suspend fun getUserByID(id: String) = requestObject.getUserById(id)

    suspend fun getJoinedRoomFutureById(id: String) : RoomFutureImpl? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    fun sync() = GlobalScope.launch {
        syncObject.sync()
    }

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val TIMEOUT = "10000"
    }
}
