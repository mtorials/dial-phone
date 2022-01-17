package de.mtorials.dialphone

import de.mtorials.dialphone.listener.Command
import de.mtorials.dialphone.listener.Listener
import kotlinx.serialization.modules.SerializersModule

interface DialPhoneBuilder {
    /**
     * Authenticate as guest user
     * Can be used as an alternative to isUser() and withToken()
     */
    fun isGuest()

    /**
     * Authenticate as user with credentials
     * @param createUserIfNotRegistered creates a user in case the user os not already registered, default is false
     * Can be used as an alternative to isGuest() and withToken()
     */
    fun isUser(username: String, password: String, createUserIfNotRegistered: Boolean = false)

    /**
     * Authenticate a user with access token
     * @param token The access token can be retrieved using a matrix client like element
     * Can be used as an alternative to usGuest() and isUser()
     */
    fun withToken(token: String)

    /**
     * Add listener and subclasses
     */
    fun addListeners(vararg listeners: Listener)

    /**
     * Can be used to make custom events serializable and extend the functionalities of the library.
     * Refer to te README for more detailed information on how to use custom events
     */
    fun addCustomSerializersModule(serializersModule: SerializersModule)

    /**
     * Builder specific to bots
     */
    fun bot(block: BotBuilder.() -> Unit)

    /**
     * Builder class for bots
     */
    interface BotBuilder {

        /**
         * Reference to this for convenience
         */
        val bot: BotBuilder

        /**
         * The prefix used to recognize commands
         * The default is "!"
         */
        var commandPrefix: String

        /**
         * When called, will generate a help command that lists all commands
         */
        fun generateHelp()

        /**
         * Used to add commands
         */
        fun commands(vararg commands: Command)
    }
}