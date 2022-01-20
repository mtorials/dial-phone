package de.mtorials.dialhone.bot


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