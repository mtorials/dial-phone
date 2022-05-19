package de.mtorials.dialphone.encyption.olm

sealed class Verification {
    data class SasV1(
        val sas: Sas
    ) : Verification()
    data class QrCodeV1(
        val qrcode: QrCode
    ) : Verification()
}