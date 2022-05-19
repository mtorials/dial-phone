package de.mtorials.dialphone.encyption.olm

data class CrossSigningStatus(
    var hasMaster: Boolean,
    var hasSelfSigning: Boolean,
    var hasUserSigning: Boolean
)