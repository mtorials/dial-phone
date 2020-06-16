package de.mtorials.dial.mevents.room

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dial.enums.JoinRule
import de.mtorials.dial.mevents.MatrixEvent

@JsonTypeName("m.room.join_rules")
class MRoomJoinRules(
    sender: String,
    @JsonProperty("event_id")
    val id: String,
    override val content: Content
) : MatrixEvent(sender, content) {
    data class Content(
        @JsonProperty("join_rule")
        val joinRule: JoinRule
    )
}