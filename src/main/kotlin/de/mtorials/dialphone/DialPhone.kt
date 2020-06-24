package de.mtorials.dialphone

import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.Listener
import kotlinx.coroutines.Job

interface DialPhone {

    val token: String
    val ownId: String
    val homeServerUrl: String
    val commandPrefix: String
    val requestObject: APIRequests

    fun sync() : Job
    fun addListener(listener: Listener)

    suspend fun getJoinedRoomFutures() : List<RoomFuture>
    suspend fun getUserById(id: String) : User?
    suspend fun getJoinedRoomFutureById(id: String) : RoomFuture?

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val TIMEOUT = "10000"
    }
}