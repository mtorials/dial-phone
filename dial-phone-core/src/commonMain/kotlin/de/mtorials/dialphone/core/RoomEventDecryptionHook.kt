package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.RoomEventHook
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted

class RoomEventDecryptionHook : RoomEventHook {
    override fun manipulateEvent(event: MatrixEvent): MatrixEvent {
        if (event !is MRoomEncrypted) return event
        TODO("encryption not implemented")
    }
}