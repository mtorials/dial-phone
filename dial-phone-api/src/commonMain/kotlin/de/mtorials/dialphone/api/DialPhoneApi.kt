package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.Listener
import kotlinx.coroutines.Job

interface DialPhoneApi {

    /**
     * The token for the matrix account
     */
    val token: String

    /**
     * Profile information
     */
    val profile: Profile

    /**
     * The users fully qualified matrix id
     */
    val ownId: String

    /**
     * The url of the homeserver
     */
    val homeserverUrl: String

    /**
     * The object the api uses to make low level requests
     */
    val apiRequests: APIRequests

//    /**
//     * The object to handle the matrix sync api
//     */
//    val synchronizer: Synchronizer

    /**
     * Used to synchronize with the homeserver.
     * Call this method to receive events.
     * To use the returning job you need kotlinx.coroutines.
     * @see sync for a blocking method
     * @return Returns an infinite lasting job
     */
    fun syncAndReturnJob() : Job

    /**
     * Used to synchronize with the homeserver.
     * Call this method to receive events.
     * This method is blocking.
     * @see syncAndReturnJob for a non-blocking method
     */
    suspend fun sync()

    /**
     * Stops syncing with the homeserver and ends the SyncJob returned syncAndReturnJob()
     */
    fun stop()

    /**
     * Register a listener
     */
    fun addListener(listener: Listener)

    companion object {
        const val MATRIX_PATH = "/_matrix/client/r0/"
        const val MATRIX_PATH_V3 = "/_matrix/client/v3/"
        const val TIMEOUT = "8000"

        suspend operator fun invoke(
            homeserverUrl: String,
            block: DialPhoneApiBuilder.() -> Unit
        ) : DialPhoneApi = DialPhoneApiBuilderImpl(
            homeserverUrl = homeserverUrl
        ).buildDialPhoneApi(block)
    }
}