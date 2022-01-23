package de.mtorials.dialphone.core.exceptions

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted

class MalformedEncryptedEvent(
    val event: MRoomEncrypted,
) : RuntimeException("The event does not contain all necessary data to decrypt it.")