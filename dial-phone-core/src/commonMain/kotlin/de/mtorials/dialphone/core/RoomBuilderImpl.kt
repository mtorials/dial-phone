package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.RoomEncryptionAlgorithm
import de.mtorials.dialphone.api.model.enums.RoomVisibility
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomEncryption
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.requests.RoomCreateRequest
import de.mtorials.dialphone.core.entities.User

class RoomBuilderImpl(private val name: String) : RoomBuilder {

    private val invites: MutableList<UserId> = mutableListOf()
    private var visibility: RoomVisibility = RoomVisibility.PRIVATE
    private val state: MutableList<MatrixStateEvent> = mutableListOf()

    override fun invite(vararg users: User) {
        this.invite(*users.map { it.id }.toTypedArray())
    }

    override fun invite(vararg users: UserId) {
        invites.addAll(users)
    }

    override var topic: String = ""
    override var alias: String = ""

    override fun makePublic() {
        visibility = RoomVisibility.PUBLIC
    }

    override fun encrypt() {
        state.add(MRoomEncryption(
            sender = UserId(""),
            stateKey = "",
            content = MRoomEncryption.Content(
                algorithm = RoomEncryptionAlgorithm.MEGOLM_V1_AES_SHA2,
            ),
            originServerTs = 0,
        ))
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