package de.mtorials.dialphone.encyption.exceptions

import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MalformedEncryptedEvent(
    val event: MRoomEncrypted,
) : EncryptionException("The event does not contain all necessary data to decrypt it: \n" + Json.encodeToString(event))