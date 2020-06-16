package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.enums.JoinRule
import de.mtorials.dialphone.mevents.MatrixEvent

interface Room : Entity, RoomActions {
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}