package de.mtorials.dialphone.olmmachine.bindings

sealed class UserIdentity {
    data class Own(
        val userId: String,
        val trustsOurOwnDevice: Boolean,
        val masterKey: String,
        val selfSigningKey: String,
        val userSigningKey: String
    ) : UserIdentity()
    data class Other(
        val userId: String,
        val masterKey: String,
        val selfSigningKey: String
    ) : UserIdentity()
}