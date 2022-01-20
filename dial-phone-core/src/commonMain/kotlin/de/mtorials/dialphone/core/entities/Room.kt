package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.model.enums.JoinRule
import de.mtorials.dialphone.core.actions.RoomActions

/**
 * A matrix room
 */
interface Room : Entity, RoomActions {
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}