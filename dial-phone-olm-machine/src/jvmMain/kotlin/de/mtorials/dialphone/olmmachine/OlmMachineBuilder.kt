package de.mtorials.dialphone.olmmachine

import de.mtorials.dialphone.olmmachine.bindings.OlmMachineInterface
import uniffi.olm.OlmMachine

actual object OlmMachineBuilder {
    actual fun create(userId: String, deviceId: String, path: String, passphrase: String?): OlmMachineInterface =
        OlmMachine(userId, deviceId, path, passphrase)

}