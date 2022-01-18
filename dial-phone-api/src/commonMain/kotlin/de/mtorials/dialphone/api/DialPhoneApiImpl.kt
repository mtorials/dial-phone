package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.Listener
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialPhoneApiImpl internal constructor(
    override val token: String,
    override val homeserverUrl: String,
    listeners: List<Listener>,
    //override val commandPrefix: String,
    override val ownId: String,
    private val client: HttpClient,
    protected val initCallback: suspend (DialPhoneApi) -> Unit,
) : DialPhoneApi {

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
