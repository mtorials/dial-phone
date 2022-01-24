package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.DialPhoneApiImpl
import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.responses.DiscoveredRoom
import de.mtorials.dialphone.api.responses.UserWithoutIDResponse
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.encryption.*
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.ids.RoomAlias
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.core.ids.roomId
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class DialPhoneImpl internal constructor(
    token: String,
    homeserverUrl: String,
    ownId: String,
    client: HttpClient,
    initCallback: suspend (DialPhoneApi) -> Unit,
    val cache: PhoneCache?,
    coroutineScope: CoroutineScope,
    private val useEncryption: Boolean = true,
    deviceId: String?,
    dialPhoneJson: Json,
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    ownId = ownId,
    client = client,
    initCallback = initCallback,
    coroutineScope = coroutineScope,
    deviceId = deviceId,
) {

    // E2EE
    // TODO impl keystore
    private val encryptionManager = EncryptionManager(
        store = InMemoryE2EEStore(),
        client = E2EEClient(client, token, homeserverUrl),
        deviceId = deviceId ?: throw RuntimeException("DialPhone has no device id. This is possible if used as an appservice"),
        ownId = ownId,
        phone = this,
        dialPhoneJson = dialPhoneJson,
    )
    private val decryptionHook = RoomEventDecryptionHook(encryptionManager)

    override val synchronizer = Synchronizer(
        this, client,
        initCallback = initCallback,
        roomEventHook = if (useEncryption) decryptionHook else null
    )

    init {
        synchronizer.addListener(EncryptionListener(encryptionManager))
        coroutineScope.launch { encryptionManager.publishKeys() }
    }

    // TODO cast is unchecked, when can it fail?
    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach { synchronizer.addListener(it as GenericListener<DialPhoneApi>) }
    }

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = synchronizer.joinedRoomIds.map {
        RoomFutureImpl(it.roomId(),this)
    }
    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions> = synchronizer.invitedRoomIds.map {
        InvitedRoomActionsImpl(this, it.roomId())
    }

    override suspend fun getUserById(id: UserId) : User? {
        // Check cache
        if (cache?.users?.containsKey(id.toString()) == true) return cache.users[id.toString()]

        val u : UserWithoutIDResponse = apiRequests.getUserById(id.toString()) ?: return null
        return UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getRoomByAlias(alias: RoomAlias): InvitedRoomActions =
        InvitedRoomActionsImpl(
            this,
            apiRequests.getRoomIdForAlias(alias.toString()).roomId.roomId()
        )

    override suspend fun getJoinedRoomFutureById(id: RoomId) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit): RoomFuture =
        apiRequests.createRoom(RoomBuilderImpl(name).apply(block).build())
            .run { RoomFutureImpl(this.id.roomId(), this@DialPhoneImpl) }


    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return apiRequests.discoverRooms().rooms.map { Pair(
            InvitedRoomActionsImpl(
                this,
                it.id.roomId()
            ), it) }
    }
}