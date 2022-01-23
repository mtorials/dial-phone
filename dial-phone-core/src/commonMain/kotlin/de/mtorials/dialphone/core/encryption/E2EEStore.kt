package de.mtorials.dialphone.core.encryption

import io.github.matrixkt.olm.Session

interface E2EEStore {
    val olmSessionsBySenderKey: MutableMap<String, Session>
    val megolmSessions: MutableList<MegolmSession>
}