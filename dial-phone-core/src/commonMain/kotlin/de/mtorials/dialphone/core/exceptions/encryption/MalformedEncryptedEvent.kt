package de.mtorials.dialphone.core.exceptions.encryption

import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted

class MalformedEncryptedEvent(
    val event: MRoomEncrypted,
) : EncryptionException("The event does not contain all necessary data to decrypt it.")