package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.listener.Listener
import de.mtorials.dialphone.core.responses.DiscoveredRoom
import kotlinx.coroutines.Job

interface DialPhone {

    /**
     * The token for the matrix account
     */
    val token: String

    /**
     * Profile information
     */
    val profile: Profile

    /**
     * The users fully qualified matrix id
     */
    val ownId: String

    /**
     * The url of the homeserver
     */
    val homeserverUrl: String


    /**
     * The object the api uses to make low level requests
     */
    val requestObject: APIRequests

    /**
     * Used to synchronize with the homeserver.
     * Call this method to receive events.
     * To use the returning job you need kotlinx.coroutines.
     * @see sync for a blocking method
     * @return Returns an infinite lasting job
     */
    fun syncAndReturnJob() : Job

    /**
     * Used to synchronize with the homeserver.
     * Call this method to receive events.
     * This method is blocking.
     * @see syncAndReturnJob for a non-blocking method
     */
    suspend fun sync()

    /**
     * Stops syncing with the homeserver and ends the SyncJob returned syncAndReturnJob()
     */
    fun stop()

    /**
     * Register a listener
     */
    fun addListener(listener: Listener)

    /**
     * @return Returns the entity futures of all rooms the user has joined
     */
    suspend fun getJoinedRoomFutures() : List<de.mtorials.dialphone.core.entities.entityfutures.RoomFuture>

    /**
     * @return Returns the room actions for all invited rooms
     */
    suspend fun getInvitedRoomActions() : List<de.mtorials.dialphone.core.entities.actions.InvitedRoomActions>

    /**
     * @param id The id of the user you want to get
     * @return Return null if not found, otherwise the user object
     */
    suspend fun getUserById(id: String) : de.mtorials.dialphone.core.entities.User?

    /**
     * @param alias The room alias
     * @return Returns the object to join the room
     */
    suspend fun getRoomByAlias(alias: String) : de.mtorials.dialphone.core.entities.actions.InvitedRoomActions

    /**
     * @param id The id of the room you want to get
     * @return Returns the room future. If not found: null
     */
    suspend fun getJoinedRoomFutureById(id: String) : de.mtorials.dialphone.core.entities.entityfutures.RoomFuture?

    /**
     * @return Returns a list of Pairs containing first the room action which can be used to join the room.
     * @return Returns the actual information as seconds
     */
    suspend fun discoverRooms() : List<Pair<de.mtorials.dialphone.core.entities.actions.InvitedRoomActions, DiscoveredRoom>>

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val TIMEOUT = "8000"

        suspend operator fun invoke(homeserverUrl: String, block: DialPhoneBuilder.() -> Unit) : DialPhone = DialPhoneBuilderImpl(
            block,
            homeserverUrl = homeserverUrl
        ).build()
    }
}