package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId
import net.folivo.trixnity.olm.OlmInboundGroupSession
import net.folivo.trixnity.olm.OlmOutboundGroupSession
import net.folivo.trixnity.olm.OlmSession

interface E2EEStore {
    val olmSessionsBySenderKey: MutableMap<String, OlmSession>
    val inboundSessionsBySessionId: MutableMap<String, OlmInboundGroupSession>
    val outboundSessionByRoomId: MutableMap<RoomId, OlmOutboundGroupSession>
    val encryptedRooms: MutableList<RoomId>
}