package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.GenericListener
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
     * The device id
     * Null in case of an application service or similar
     */
    val deviceId: String?

    /**
     * The url of the homeserver
     */
    val homeserverUrl: String

    /**
     * The object the api uses to make low level requests
     */
    val apiRequests: APIRequests


    /**
     * Used to synchronize with the homeserver.
     * Call this method to receive events.
     */
    fun sync() : Job

    /**
     * Register a listener
     */
    fun addListeners(vararg listener: GenericListener<*>)

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