package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneBuilder
import de.mtorials.dialphone.api.DialPhoneBuilderImpl
import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.responses.DiscoveredRoom
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entityfutures.RoomFuture


interface DialPhone : DialPhoneApi {

    /**
     * The cache
     */
    val cache: PhoneCache

    /**
     * @return Returns the entity futures of all rooms the user has joined
     */
    suspend fun getJoinedRoomFutures() : List<RoomFuture>

    /**
     * @return Returns the room actions for all invited rooms
     */
    suspend fun getInvitedRoomActions() : List<InvitedRoomActions>

    /**
     * @param id The id of the user you want to get
     * @return Return null if not found, otherwise the user object
     */
    suspend fun getUserById(id: String) : User?

    /**
     * @param alias The room alias
     * @return Returns the object to join the room
     */
    suspend fun getRoomByAlias(alias: String) : InvitedRoomActions

    /**
     * @param id The id of the room you want to get
     * @return Returns the room future. If not found: null
     */
    suspend fun getJoinedRoomFutureById(id: String) : RoomFuture?

    /**
     * @return Returns a list of Pairs containing first the room action which can be used to join the room.
     * @return Returns the actual information as seconds
     */
    suspend fun discoverRooms() : List<Pair<InvitedRoomActions, DiscoveredRoom>>

    companion object {
        suspend operator fun invoke(homeserverUrl: String, block: DialPhoneBuilder.() -> Unit) : DialPhone {
            val dialPhoneCore = DialPhoneBuilderImpl(
                block,
                homeserverUrl = homeserverUrl
            ).build()
            return DialPhoneImpl(dialPhoneCore)
        }
    }
}
