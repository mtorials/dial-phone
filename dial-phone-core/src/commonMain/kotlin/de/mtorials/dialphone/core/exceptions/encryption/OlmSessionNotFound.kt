package de.mtorials.dialphone.core.exceptions.encryption

import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted

class OlmSessionNotFound(
    val event: MRoomEncrypted,
) : EncryptionException("No olm session found for non pre-key messages.")