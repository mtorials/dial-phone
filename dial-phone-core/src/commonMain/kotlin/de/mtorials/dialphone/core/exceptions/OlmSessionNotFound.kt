package de.mtorials.dialphone.core.exceptions

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted

class OlmSessionNotFound(
    val event: MRoomEncrypted,
) : RuntimeException("No olm session found for non pre-key messages.")