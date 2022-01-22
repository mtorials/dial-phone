package de.mtorials.dialphone.core.encryption

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object EncryptionUtilities {
    inline fun <reified T> getCanonicalJson(obj: T) : String  {
        val jsonElement = Json.encodeToJsonElement(obj)
        return Json.encodeToString(CanonicalSerializer(), jsonElement)
    }
}

// TODO TESTING!
class CanonicalSerializer : KSerializer<JsonElement> by JsonElement.serializer() {
    override fun serialize(encoder: Encoder, value: JsonElement) {
        val e = buildJsonObject {
            value.jsonObject.entries.sortedBy { it.key }.forEach { (key, value) ->
                put(key, value)
            }
        }
        return JsonElement.serializer().serialize(encoder, e)
    }
}