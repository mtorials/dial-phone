package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.listeners.ApiListener
import io.ktor.client.*
import kotlinx.coroutines.*

open class DialPhoneApiImpl constructor(
    final override val token: String,
    final override val homeserverUrl: String,
    //override val commandPrefix: String,
    override val ownId: String,
    protected val client: HttpClient,
    protected val initCallback: suspend (DialPhoneApi) -> Unit,
    protected val coroutineDispatcher: CoroutineDispatcher,
    override val deviceId: String?,
) : DialPhoneApi {

    override val apiRequests = APIRequests(
        homeserverUrl = homeserverUrl,
        token = token,
        client = client
    )

    override val profile = Profile(this)

    protected open val synchronizer = Synchronizer(this, client, initCallback = initCallback)

    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach {
            if (it !is ApiListener) error("This listener is not supported on DialPhoneApi, use DialPhone instead.")
            synchronizer.addListener(it)
        }
    }

    override fun sync(): Job = synchronizer.sync(GlobalScope, coroutineDispatcher)

}
