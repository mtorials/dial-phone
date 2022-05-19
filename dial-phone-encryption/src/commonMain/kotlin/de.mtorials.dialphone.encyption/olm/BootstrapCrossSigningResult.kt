package de.mtorials.dialphone.encyption.olm

data class BootstrapCrossSigningResult(
    var uploadSigningKeysRequest: UploadSigningKeysRequest,
    var signatureRequest: SignatureUploadRequest
)