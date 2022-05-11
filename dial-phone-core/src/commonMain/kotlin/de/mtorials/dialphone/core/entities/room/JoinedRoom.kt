package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.JoinRule
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.StateEventContent
import de.mtorials.dialphone.core.entities.Entity
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.Message

/**
 * A matrix room
 */
interface JoinedRoom : Entity {
    override val id: RoomId
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule

    /**
     * All matrix state events corresponding to this room
     */
    val stateEvents: List<MatrixStateEvent>

    /**
     * Sends an MatrixMessageEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @return The event Id
     */
    suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : EventId

    /**
     * Sends an MatrixStateEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @param stateKey The state key. Mostly empty. See matrix specifications
     * @return The event Id
     */
    suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String) : EventId

    /**
     * Sends an MatrixStateEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @param stateKey The state key. default is user id
     * @return The event Id
     */
    suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: UserId = phone.ownId) : EventId

    /**
     * Leave the room
     */
    suspend fun leave()
}