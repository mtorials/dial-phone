package de.mtorials.dialphone.encyption.olm

expect object OlmMachineBuilder {
    fun create(userId: String, deviceId: String, path: String, passphrase: String?) : OlmMachineInterface
}