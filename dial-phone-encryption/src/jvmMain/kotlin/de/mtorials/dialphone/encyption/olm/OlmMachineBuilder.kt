package de.mtorials.dialphone.encyption.olm

import uniffi.olm.OlmMachine

actual object OlmMachineBuilder {
    actual fun create(userId: String, deviceId: String, path: String, passphrase: String?) : OlmMachineInterface
        = OlmMachine(userId, deviceId, path, passphrase)
}