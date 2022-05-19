package de.mtorials.dialphone.encyption.olm

data class CrossSigningKeyExport(
    var masterKey: String?,
    var selfSigningKey: String?,
    var userSigningKey: String?
)