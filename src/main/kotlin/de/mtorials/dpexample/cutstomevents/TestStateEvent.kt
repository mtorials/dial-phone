package de.mtorials.dpexample.cutstomevents

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.mevents.roomstate.StateEventContent

@JsonTypeName("de.mtorials.matrix.events.state.test")
class TestStateEvent(
    @JsonProperty("state_key")
    override val stateKey: String,
    @JsonProperty("prev_content")
    override val prevContent: Content?,
    override val content: Content,
    @JsonProperty("event_id")
    override val id: String,
    override val sender: String
) : MatrixStateEvent{
    @ContentEventType(TestStateEvent::class)
    data class Content(
        @JsonProperty("test_string")
        val testString: String
    ) : StateEventContent
}