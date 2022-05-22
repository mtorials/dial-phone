package de.mtorials.dialphone.olmmachine.bindings

data class StartSasResult(
    var sas: Sas,
    var request: OutgoingVerificationRequest
)