package de.mtorials.dialphone.core.encryption

import io.github.matrixkt.olm.Session

class InMemoryE2EEStore : E2EEStore{
    override val olmSessionsBySenderKey: MutableMap<String, Session> = mutableMapOf()
    override val megolmSessions: MutableList<MegolmSession> = mutableListOf()
}