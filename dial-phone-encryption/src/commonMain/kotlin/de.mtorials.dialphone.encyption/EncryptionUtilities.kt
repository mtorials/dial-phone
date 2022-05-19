package de.mtorials.dialphone.encyption

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object EncryptionUtilities {
    inline fun <reified T> getCanonicalJson(obj: T) : String  {
        val jsonElement = Json.encodeToJsonElement(obj)
        return Json.encodeToString(CanonicalSerializer(), jsonElement)
    }
}

// UNIT TESTS!
class CanonicalSerializer : KSerializer<JsonElement> by JsonElement.serializer() {
    override fun serialize(encoder: Encoder, value: JsonElement) {
        val e = sortJsonElement(value)
        return JsonElement.serializer().serialize(encoder, e)
    }

    private fun sortJsonElement(value: JsonElement) : JsonElement {
        if (value is JsonObject) {
            val obj = buildJsonObject {
                value.entries.sortedBy { (key, _) ->
                    key
                }.forEach { (key, el) ->
                    val sortedEl = if (el is JsonObject) sortJsonElement(el) else el
                    put(key, sortedEl)
                }
            }
            return obj
        }
        return value
    }
}