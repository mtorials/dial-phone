package de.mtorials.dialphone.encyption.olm

data class UploadSigningKeysRequest(
    var masterKey: String,
    var selfSigningKey: String,
    var userSigningKey: String
)