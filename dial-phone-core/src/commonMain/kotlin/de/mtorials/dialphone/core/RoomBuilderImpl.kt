package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.model.enums.RoomVisibility
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.requests.RoomCreateRequest
import de.mtorials.dialphone.core.entityfutures.UserFuture

class RoomBuilderImpl(private val name: String) : RoomBuilder {

    private val invites: MutableList<String> = mutableListOf()
    private var visibility: RoomVisibility = RoomVisibility.PRIVATE
    private val state: MutableList<MatrixStateEvent> = mutableListOf()

    override fun invite(vararg users: UserFuture) {
        invites.addAll(users.map { it.userId.toString() })
    }

    override var topic: String = ""
    override var alias: String = ""

    override fun makePublic() {
        visibility = RoomVisibility.PUBLIC
    }


    override fun initialStateEvents(vararg events: MatrixStateEvent) {
        state.addAll(events)
    }

    fun build() = RoomCreateRequest(
        name = name,
        invite = invites,
        initialState = state,
        visibility = visibility,
        isDirect = true,
        alias = alias.ifEmpty { null },
        topic = topic.ifEmpty { null },
    )
}