package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.model.enums.JoinRule

/**
 * A matrix room
 */
interface Room : de.mtorials.dialphone.core.entities.Entity, de.mtorials.dialphone.core.entities.actions.RoomActions {
    val name: String
    val members: List<de.mtorials.dialphone.core.entities.Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}