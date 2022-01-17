package de.mtorials.dialphone.entities.entities

import de.mtorials.dialphone.core.model.enums.JoinRule
import de.mtorials.dialphone.entities.actions.RoomActions

/**
 * A matrix room
 */
interface Room : Entity, RoomActions {
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}