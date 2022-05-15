package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId

import net.folivo.trixnity.olm.OlmInboundGroupSession
import net.folivo.trixnity.olm.OlmOutboundGroupSession
import net.folivo.trixnity.olm.OlmSession

class InMemoryE2EEStore : E2EEStore {
    override val olmSessionsBySenderKey: MutableMap<String, OlmSession> = mutableMapOf()
    override val inboundSessionsBySessionId: MutableMap<String, OlmInboundGroupSession> = mutableMapOf()
    override val outboundSessionByRoomId: MutableMap<RoomId, OlmOutboundGroupSession> = mutableMapOf()
    override val encryptedRooms: MutableList<RoomId> = mutableListOf()
}