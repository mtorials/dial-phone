package de.mtorials.dialphone.olmmachine.bindings

data class PassphraseInfo(
    var privateKeySalt: String,
    var privateKeyIterations: Int
)