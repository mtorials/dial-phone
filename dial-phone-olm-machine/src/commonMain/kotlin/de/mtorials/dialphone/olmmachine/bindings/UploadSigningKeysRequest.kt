package de.mtorials.dialphone.olmmachine.bindings

data class UploadSigningKeysRequest(
    var masterKey: String,
    var selfSigningKey: String,
    var userSigningKey: String
)