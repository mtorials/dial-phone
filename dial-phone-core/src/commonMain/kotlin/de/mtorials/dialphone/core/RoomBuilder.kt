package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.entities.User

interface RoomBuilder {

    /**
     * Users to invite
     */
    fun invite(vararg users: User)

    /**
     * The topic of the room. default is empty
     */
    var topic: String

    /**
     * The alias of the room
     */
    var alias: String

    /**
     * Makes the room a public room, default is private
     */
    fun makePublic()

    /**
     * Initialize room with special state events
     */
    fun initialStateEvents(vararg events: MatrixStateEvent)
}