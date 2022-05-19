package de.mtorials.dialphone.encyption.olm

data class PassphraseInfo(
    var privateKeySalt: String,
    var privateKeyIterations: Int
)