package de.mtorials.dialphone

import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.Listener
import kotlinx.coroutines.Job

interface DialPhone {

    /**
     * The token for the matrix account
     */
    val token: String

    /**
     * The id of the account you logged in with
     */
    val ownId: String

    /**
     * The url of the homeserver
     */
    val homeserverUrl: String

    /**
     * The command prefix you specified optionally
     */
    val commandPrefix: String

    /**
     * The object the api uses to make low level requests
     */
    val requestObject: APIRequests

    /**
     * Used to synchronize with the homeserver.
     * Call this function to receive events
     * @return Return an infinite lasting job
     */
    fun sync() : Job

    /**
     * Adds a listener
     */
    fun addListener(listener: Listener)

    /**
     * @return Return the entity futures of all rooms the user has joined
     */
    suspend fun getJoinedRoomFutures() : List<RoomFuture>

    /**
     * @param id The id of the user you want to get
     * @return Return null if not found, otherwise the user object
     */
    suspend fun getUserById(id: String) : User?

    /**
     * @param id The id of the room you want to get
     * @return Returns the room future. If not found: null
     */
    suspend fun getJoinedRoomFutureById(id: String) : RoomFuture?

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val TIMEOUT = "10000"

        operator fun invoke(block: DialPhoneBuilder.() -> Unit) : DialPhone = DialPhoneBuilder(block).build()
    }
}