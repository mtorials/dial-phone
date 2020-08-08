package de.mtorials.dialphone


import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.UserImpl
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import kotlinx.coroutines.runBlocking
import de.mtorials.dialphone.listener.Listener
import net.micromes.makocommons.mevents.MatrixEvent
import de.mtorials.dialphone.responses.DiscoveredRoom
import de.mtorials.dialphone.responses.UserWithoutIDResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class DialPhoneImpl internal constructor(
    override val token: String,
    override val homeserverUrl: String,
    listeners: List<Listener>,
    override val commandPrefix: String,
    customEventTypes: Array<KClass<out MatrixEvent>>,
    userId: String? = null
) : DialPhone {

    // cache
    val cache = PhoneCache()

    override val requestObject = APIRequests(this, customEventTypes)

    override val ownId: String = userId ?: runBlocking {
        requestObject.getMe().id
    }

    override val profile = Profile(this, ownId)

    private val syncObject = Synchronizer(listeners.toMutableList(), this, customEventTypes)

    override fun addListener(listener: Listener) {
        syncObject.addListener(listener)
    }

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = cache.joinedRooms
    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions> = cache.invitedRooms

    //override suspend fun getJoinedRoomFutures() : List<RoomFuture> =
    //    requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhoneImpl) }

    override suspend fun getUserById(id: String) : User? {
        val u : UserWithoutIDResponse = requestObject.getUserById(id) ?: return null
        return UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getRoomByAlias(alias: String): InvitedRoomActions =
        InvitedRoomActionsImpl(this, requestObject.getRoomIdForAlias(alias).roomId)

    override suspend fun getJoinedRoomFutureById(id: String) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return requestObject.discoverRooms().rooms.map { Pair(InvitedRoomActionsImpl(this, it.id), it) }
    }

    override fun sync() = GlobalScope.launch {
        syncObject.sync()
    }
}
