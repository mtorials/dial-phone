package de.mtorials.dialphone.olmmachine.bindings

data class CrossSigningKeyExport(
    var masterKey: String?,
    var selfSigningKey: String?,
    var userSigningKey: String?
)