package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.listener.Listener
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DialPhoneCoreImpl internal constructor(
    override val token: String,
    override val homeserverUrl: String,
    listeners: List<Listener>,
    //override val commandPrefix: String,
    override val ownId: String,
    private val client: HttpClient,
    initCallback: suspend (DialPhoneCore) -> Unit,
) : DialPhoneCore {

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
