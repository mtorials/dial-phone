package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoomEncryptionAlgorithm {
    @SerialName("m.megolm.v1.aes-sha2")
    MEGOLM_V1_AES_SHA2
}