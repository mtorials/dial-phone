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
            phone.stop()
        })
        println("Sending msg")
        val room: RoomFuture = phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de") ?: error("Room not found")
        room.sendTextMessage(testString)
        job.join()
    }
}