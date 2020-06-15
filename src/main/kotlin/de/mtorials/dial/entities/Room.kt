package de.mtorials.dial.entities

import de.mtorials.dial.enums.JoinRule

interface Room : Entity {
    val name: String
    val members: List<Member>
    val avatarUrl: String?
    val joinRule: JoinRule
}