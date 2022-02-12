package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId
import io.github.matrixkt.olm.InboundGroupSession
import io.github.matrixkt.olm.OutboundGroupSession
import io.github.matrixkt.olm.Session

interface E2EEStore {
    val olmSessionsBySenderKey: MutableMap<String, Session>
    val inboundSessionsBySessionId: MutableMap<String, InboundGroupSession>
    val outboundSessionByRoomId: MutableMap<RoomId, OutboundGroupSession>
    val encryptedRooms: MutableList<RoomId>
}