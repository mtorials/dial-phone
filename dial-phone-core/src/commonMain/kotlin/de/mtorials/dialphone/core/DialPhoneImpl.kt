package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.listener.Listener
import de.mtorials.dialphone.core.responses.DiscoveredRoom
import de.mtorials.dialphone.core.responses.UserWithoutIDResponse
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialPhoneImpl internal constructor(
    override val token: String,
    override val homeserverUrl: String,
    listeners: List<Listener>,
    //override val commandPrefix: String,
    override val ownId: String,
    private val client: HttpClient,
    initCallback: suspend (DialPhone) -> Unit,
) : DialPhone {

    // cache
    val cache = PhoneCache()

    override val requestObject = APIRequests(
        homeserverUrl = homeserverUrl,
        token = token,
        client = client
    )

    override val profile = Profile(this)

    private val syncObject = Synchronizer(listeners.toMutableList(), this, client, initCallback = initCallback)

    override fun addListener(listener: Listener) {
        syncObject.addListener(listener)
    }

    override suspend fun getJoinedRoomFutures(): List<de.mtorials.dialphone.core.entities.entityfutures.RoomFuture> = requestObject.getJoinedRooms().roomIds.map {
        de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(
            it,
            this
        )
    }
    override suspend fun getInvitedRoomActions(): List<de.mtorials.dialphone.core.entities.actions.InvitedRoomActions> = cache.invitedRooms

    //override suspend fun getJoinedRoomFutures() : List<RoomFuture> =
    //    requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhoneImpl) }

    override suspend fun getUserById(id: String) : de.mtorials.dialphone.core.entities.User? {
        // Check cache
        if (cache.users.containsKey(id)) return cache.users[id]

        val u : UserWithoutIDResponse = requestObject.getUserById(id) ?: return null
        return de.mtorials.dialphone.core.entities.UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getRoomByAlias(alias: String): de.mtorials.dialphone.core.entities.actions.InvitedRoomActions =
        de.mtorials.dialphone.core.entities.actions.InvitedRoomActionsImpl(
            this,
            requestObject.getRoomIdForAlias(alias).roomId
        )

    override suspend fun getJoinedRoomFutureById(id: String) : de.mtorials.dialphone.core.entities.entityfutures.RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun discoverRooms(): List<Pair<de.mtorials.dialphone.core.entities.actions.InvitedRoomActions, DiscoveredRoom>> {
        return requestObject.discoverRooms().rooms.map { Pair(
            de.mtorials.dialphone.core.entities.actions.InvitedRoomActionsImpl(
                this,
                it.id
            ), it) }
    }

    override fun syncAndReturnJob(): Job = GlobalScope.launch {
        syncObject.sync()
    }

    override suspend fun sync() {
        syncObject.sync()
    }

    override fun stop() {
        syncObject.stop()
    }
}
