package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.core.entities.room.InvitedRoom
import de.mtorials.dialphone.core.entities.room.JoinedRoom


interface DialPhone : DialPhoneApi {

    /**
     * @return Returns the entity futures of all rooms the user has joined
     */
    suspend fun getJoinedRoomFutures() : List<JoinedRoom>

    /**
     * Returns null if no cache is used
     * @return Returns the room actions for all invited rooms
     */
    suspend fun getInvitedRoomActions() : List<InvitedRoom>?

    /**
     * @param id The id of the user you want to get
     * @return Return null if not found, otherwise the user object
     */
    suspend fun getUserById(id: UserId) : User?

    /**
     * @param alias The room alias
     * @return Returns the object to join the room
     */
    suspend fun getInvitedRoomByAlias(alias: RoomAlias) : InvitedRoom

    /**
     * @param id The id of the room you want to get
     * @return Returns the room future. If not found: null
     */
    suspend fun getJoinedRoomById(id: RoomId) : JoinedRoom?

    /**
     * Create a room
     */
    suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit) : JoinedRoom

    /**
     * @return Returns a list of Pairs containing first the room action which can be used to join the room.
     * @return Returns the actual information as seconds
     */
    suspend fun discoverRooms() : List<InvitedRoom>

    /**
     * Returns the user dial-phone is logged in with
     */
    suspend fun getMe() : User

    companion object {
        suspend operator fun invoke(homeserverUrl: String, block: DialPhoneBuilder.() -> Unit) : DialPhone {
            return DialPhoneBuilderImpl(
                homeserverUrl = homeserverUrl
            ).buildDialPhone(block)
        }
    }
}
