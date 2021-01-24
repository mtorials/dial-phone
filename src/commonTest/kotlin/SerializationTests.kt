import de.mtorials.dialphone.entities.EntitySerialization
import de.mtorials.dialphone.model.mevents.EventSerialization
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationTests {

    private val format = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
        encodeDefaults = true
        serializersModule =
            EventSerialization.serializersModule + EntitySerialization.serializersModule
    }

    @Test
    fun checkMessageContent() {
        val msgContent = format.encodeToString(MRoomMessage.TextContent(
            "Hello"
        ))
        println(msgContent)
        assertEquals("hello", msgContent)
    }
}