package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.responses.DiscoveredRoomResponse
import de.mtorials.dialphone.core.entities.room.DiscoveredRoom
import de.mtorials.dialphone.core.entities.room.InvitedRoom
import de.mtorials.dialphone.core.entities.room.JoinedRoom


interface DialPhone : DialPhoneApi {

    /**
     * @return Returns the entity futures of all rooms the user has joined
     */
    suspend fun getJoinedRooms() : List<JoinedRoom>

    /**
     * @return Returns the room actions for all invited rooms
     */
    suspend fun getInvitedRoomActions() : List<InvitedRoom>

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
     * @param id The id of the room you want to receive
     * @return Returns the room future. If not found returns null
     */
    suspend fun getJoinedRoomById(id: RoomId) : JoinedRoom?

    /**
     * @param name The name of the room you want to receive
     * @return Returns the room future. If not found returns null.
     * If multiple rooms match the name, an arbitrary one is returned
     */
    suspend fun getJoinedRoomByName(name: String, ignoreCase: Boolean = true) : JoinedRoom?

    /**
     * Create a room
     */
    suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit) : JoinedRoom

    /**
     * @return Returns a list of Pairs containing first the room action which can be used to join the room.
     * @return Returns the actual information as seconds
     */
    suspend fun discoverRooms() : List<DiscoveredRoom>

    /**
     * @return Returns the user dial-phone is logged in with
     */
    suspend fun getMe() : User

    companion object {
        suspend operator fun invoke(homeserverUrl: String, block: suspend DialPhoneBuilder.() -> Unit) : DialPhone {
            return DialPhoneBuilderImpl(
                homeserverUrl = homeserverUrl
            ).buildDialPhone(block).also { it.initialSync() }
        }
    }
}
