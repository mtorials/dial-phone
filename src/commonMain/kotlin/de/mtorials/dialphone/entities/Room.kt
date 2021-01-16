package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.model.JoinRule

/**
 * A matrix room
 */
interface Room : Entity, RoomActions {
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}