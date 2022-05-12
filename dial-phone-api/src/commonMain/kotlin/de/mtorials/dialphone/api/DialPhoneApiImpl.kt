package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.listeners.ApiListener
import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.api.model.mevents.EventContent
import io.ktor.client.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

open class DialPhoneApiImpl constructor(
    final override val token: String,
    final override val homeserverUrl: String,
    //override val commandPrefix: String,
    override val ownId: UserId,
    protected val client: HttpClient,
    protected val initCallback: suspend (DialPhoneApi) -> Unit,
    protected val coroutineScope: CoroutineScope,
    override val deviceId: String?,
    val format: Json,
    val logLevel: DialPhoneLogLevel,
) : DialPhoneApi {

    override val apiRequests = APIRequests(
        homeserverUrl = homeserverUrl,
        token = token,
        client = client
    )

    override suspend fun sendMessageEvent(roomId: RoomId, type: String, content: EventContent) : EventId {
        return apiRequests.sendMessageEvent(type, content, roomId)
    }

    override val profile = Profile(this)

    protected open val synchronizer = Synchronizer(this, client, initCallback = initCallback, logLevel = logLevel)

    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach {
            if (it !is ApiListener) error("This listener is not supported on DialPhoneApi, use DialPhone instead.")
            synchronizer.addListener(it)
        }
    }

    override fun sync(): Job = synchronizer.sync(coroutineScope)

    suspend fun initialSync() {
        synchronizer.sync(coroutineScope, true).join()
    }
}
