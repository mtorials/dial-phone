package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.EventContent
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
    val ownId: UserId

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
     * Send an event to a room
     */
    suspend fun sendMessageEvent(roomId: RoomId, type: String, content: EventContent) : EventId

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
        const val MATRIX_PATH = "/_matrix/client/v3/"
        const val TIMEOUT = "8000"

        suspend operator fun invoke(
            homeserverUrl: String,
            block: DialPhoneApiBuilder.() -> Unit
        ) : DialPhoneApi = DialPhoneApiBuilderImpl(
            homeserverUrl = homeserverUrl
        ).buildDialPhoneApi(block).also { it.initialSync() }
    }
}