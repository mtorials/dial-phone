package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId
import io.github.matrixkt.olm.InboundGroupSession
import io.github.matrixkt.olm.OutboundGroupSession
import io.github.matrixkt.olm.Session

class InMemoryE2EEStore : E2EEStore {
    override val olmSessionsBySenderKey: MutableMap<String, Session> = mutableMapOf()
    override val inboundSessionsBySessionId: MutableMap<String, InboundGroupSession> = mutableMapOf()
    override val outboundSessionByRoomId: MutableMap<RoomId, OutboundGroupSession> = mutableMapOf()
    override val encryptedRooms: MutableList<RoomId> = mutableListOf()
}