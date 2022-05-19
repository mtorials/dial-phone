package de.mtorials.dialphone.encyption.olm

data class ConfirmVerificationResult(
    var requests: List<OutgoingVerificationRequest>,
    var signatureRequest: SignatureUploadRequest?
)