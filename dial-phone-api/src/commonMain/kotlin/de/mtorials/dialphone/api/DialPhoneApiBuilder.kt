package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.modules.SerializersModule

interface DialPhoneApiBuilder {
    /**
     * Authenticate as guest user
     * Can be used as an alternative to
     * @see asUser
     * @see withToken
     */
    fun asGuest()

    /**
     * Authenticate as user with credentials
     * @param createUserIfNotRegistered creates a user in case the user os not already registered, default is false
     * Can be used as an alternative to
     * @see asGuest
     * @see withToken
     */
    fun asUser(username: String, password: String, createUserIfNotRegistered: Boolean = false)

    /**
     * Authenticate a user with access token
     * @param token The access token can be retrieved using a matrix client like element
     * Can be used as an alternative to
     * @see asUser
     * @see asGuest
     */
    fun withToken(token: String)

    /**
     * Add listener and subclasses
     */
    fun addListeners(vararg listeners: GenericListener<*>)

    /**
     * Can be used to make custom events serializable and extend the functionalities of the library.
     * Refer to te README for more detailed information on how to use custom events
     */
    fun addCustomSerializersModule(serializersModule: SerializersModule)

    /**
     * Set a custom coroutine scope
     */
    fun withCoroutineDispatcher(coroutineScope: CoroutineScope)

    /**
     * Set the ktor log level
     */
    var ktorLogLevel: LogLevel

    /**
     * dial-phone log level
     */
    var dialPhoneLogLevel: DialPhoneLogLevel
}