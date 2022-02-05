package de.mtorials.dialphone.encyption.exceptions

import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted

class OlmSessionNotFound(
    val event: MRoomEncrypted,
) : EncryptionException("No olm session found for non pre-key messages.")