package de.mtorials.dialphone

import de.mtorials.dialphone.entities.EntitySerialization
import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.UserImpl
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.Listener
import net.mt32.makocommons.mevents.MatrixEvent
import de.mtorials.dialphone.responses.DiscoveredRoom
import de.mtorials.dialphone.responses.UserWithoutIDResponse
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer
import net.mt32.makocommons.EventSerialization
import net.mt32.makocommons.mevents.presence.MPresence
import net.mt32.makocommons.mevents.roommessage.MReaction
import net.mt32.makocommons.mevents.roommessage.MRoomEncrypted
import net.mt32.makocommons.mevents.roommessage.MRoomMessage
import net.mt32.makocommons.mevents.roommessage.MRoomRedaction
import net.mt32.makocommons.mevents.roomstate.*
import kotlin.reflect.KClass

class DialPhoneImpl internal constructor(
    override val token: String,
    override val homeserverUrl: String,
    listeners: List<Listener>,
    override val commandPrefix: String,
    override val ownId: String,
    private val client: HttpClient
) : DialPhone {

    // cache
    val cache = PhoneCache()

    override val requestObject = APIRequests(homeserverUrl, token, client)

    override val profile = Profile(this)

    private val syncObject = Synchronizer(listeners.toMutableList(), this, client)

    override fun addListener(listener: Listener) {
        syncObject.addListener(listener)
    }

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = cache.joinedRooms
    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions> = cache.invitedRooms

    //override suspend fun getJoinedRoomFutures() : List<RoomFuture> =
    //    requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhoneImpl) }

    override suspend fun getUserById(id: String) : User? {
        // Check cache
        if (cache.users.containsKey(id)) return cache.users[id]

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

    override fun syncAndReturnJob(): Job = GlobalScope.launch {
        syncObject.sync()
    }

    override suspend fun sync() {
        syncObject.sync()
    }
}
