package de.mtorials.dial

import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.entityfutures.UserFuture
import kotlinx.coroutines.runBlocking
import de.mtorials.dial.listener.Listener

class DialPhone(
    val token: String,
    val homeServerURL: String,
    listeners: List<Listener> = listOf(),
    val commandPrefix: String = "!"
) {

    val ownId: String
    val requestObject = APIRequests(this)

    private val syncObject = Synchronizer(listeners.toMutableList(), this)

    init {
        ownId = runBlocking {
            requestObject.getMe().id
        }
    }

    fun addListener(listener: Listener) = syncObject.addListener(listener)

    suspend fun getJoinedRoomFutures() : List<RoomFuture> =
        requestObject.getJoinedRooms().roomIds.map { id -> RoomFuture(id, this@DialPhone) }

    fun getUserFutureById(id: String) = UserFuture(id, this)

    suspend fun getJoinedRoomFutureById(id: String) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.entityId }.contains(id)) {
            true -> RoomFuture(id, this)
            false -> null
        }

    suspend fun sync() {
        syncObject.sync()
    }

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val TIMEOUT = "10000"
    }
}
