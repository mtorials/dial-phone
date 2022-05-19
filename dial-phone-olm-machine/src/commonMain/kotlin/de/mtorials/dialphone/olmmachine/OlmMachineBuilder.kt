package de.mtorials.dialphone.olmmachine

import de.mtorials.dialphone.olmmachine.bindings.OlmMachineInterface

expect object OlmMachineBuilder {
    fun create(userId: String, deviceId: String, path: String, passphrase: String?) : OlmMachineInterface
}