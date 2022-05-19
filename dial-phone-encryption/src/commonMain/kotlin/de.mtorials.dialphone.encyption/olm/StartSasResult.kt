package de.mtorials.dialphone.encyption.olm

data class StartSasResult(
    var sas: Sas,
    var request: OutgoingVerificationRequest
)