import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.dialphone.sendTextMessage
import kotlinx.coroutines.cancelChildren
import kotlin.test.assertEquals

class IntegrationTests {

    private val testString = "asidh983h54lkwnef"

    suspend fun basicTestWithToken(config: Config) {
        println("hello")
        val phone = DialPhone {
            withToken(config.token)
            homeserverUrl = config.homeserverUrl
        }
        println("created dialphone")
        val job = phone.syncAndReturnJob()
        phone.addListener(MessageListener(false) {
            println(it.message.body)
            assertEquals(testString, it.message.body)
            it.message.redact("Test")
            phone.stop()
        })
        println("Sending msg")
        val room: RoomFuture = phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de") ?: error("Room not found")

        // Test room info
        assertEquals("[Demo] Manuell", room.receive().name)
        assertEquals("@mtorials:mtorials.de", phone.getUserById("@mtorials:mtorials.de")?.id)

        room.sendTextMessage(testString)
        job.join()
    }
}