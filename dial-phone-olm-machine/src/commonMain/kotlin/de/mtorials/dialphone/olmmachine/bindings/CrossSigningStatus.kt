package de.mtorials.dialphone.olmmachine.bindings

data class CrossSigningStatus(
    var hasMaster: Boolean,
    var hasSelfSigning: Boolean,
    var hasUserSigning: Boolean
)