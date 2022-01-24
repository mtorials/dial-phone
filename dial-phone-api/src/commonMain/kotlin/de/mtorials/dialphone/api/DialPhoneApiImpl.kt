package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.listeners.ApiListener
import de.mtorials.dialphone.api.model.mevents.EventContent
import io.ktor.client.*
import kotlinx.coroutines.*

open class DialPhoneApiImpl constructor(
    final override val token: String,
    final override val homeserverUrl: String,
    //override val commandPrefix: String,
    override val ownId: String,
    protected val client: HttpClient,
    protected val initCallback: suspend (DialPhoneApi) -> Unit,
    protected val coroutineScope: CoroutineScope,
    override val deviceId: String?,
) : DialPhoneApi {

    override val apiRequests = APIRequests(
        homeserverUrl = homeserverUrl,
        token = token,
        client = client
    )

    override suspend fun sendMessageEvent(roomId: String, type: String, content: EventContent) : String {
        return apiRequests.sendMessageEvent(type, content, roomId)
    }

    override val profile = Profile(this)

    protected open val synchronizer = Synchronizer(this, client, initCallback = initCallback)

    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach {
            if (it !is ApiListener) error("This listener is not supported on DialPhoneApi, use DialPhone instead.")
            synchronizer.addListener(it)
        }
    }

    override fun sync(): Job = synchronizer.sync(coroutineScope)

}
