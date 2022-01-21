package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.Listener
import io.ktor.client.*
import kotlinx.coroutines.*

open class DialPhoneApiImpl constructor(
    final override val token: String,
    final override val homeserverUrl: String,
    listeners: List<Listener>,
    //override val commandPrefix: String,
    override val ownId: String,
    protected val client: HttpClient,
    protected val initCallback: suspend (DialPhoneApi) -> Unit,
    protected val coroutineDispatcher: CoroutineDispatcher,
) : DialPhoneApi {

    override val apiRequests = APIRequests(
        homeserverUrl = homeserverUrl,
        token = token,
        client = client
    )

    override val profile = Profile(this)

    protected open val synchronizer = Synchronizer(listeners.toMutableList(), this, client, initCallback = initCallback)

    override fun addListener(listener: Listener) {
        synchronizer.addListener(listener)
    }

    override fun sync(): Job = synchronizer.sync(GlobalScope, coroutineDispatcher)

}
