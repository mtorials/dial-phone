package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageEncryptionAlgorithm {
    @SerialName("m.olm.v1.curve25519-aes-sha2")
    OLM_V1_CURVE25519_AES_SHA1,
    @SerialName("m.megolm.v1.aes-sha2")
    MEGOLM_V1_AES_SHA2
}