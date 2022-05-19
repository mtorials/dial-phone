package de.mtorials.dialphone.encyption.olm

data class ScanResult(
    var qr: QrCode,
    var request: OutgoingVerificationRequest
)