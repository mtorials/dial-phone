import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.dialevents.RoomInviteEvent
import de.mtorials.dialphone.dialevents.answer
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.Command
import de.mtorials.dialphone.listener.ListenerAdapter
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.dialphone.listener.listenFor
import de.mtorials.dialphone.model.mevents.MatrixEvent
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.sendTextMessage
import kotlinx.coroutines.cancelChildren
import kotlin.test.assertEquals

class IntegrationTests {

    private val roomId = "!YIqYutrrBUdGDombnI:mtorials.de"
    private val testString = "asidh983h54lkwnef"

    suspend fun basicTestWithToken(config: Config) {
        println("hello")
        val phone = DialPhone {
            withToken(config.token)
            homeserverUrl = config.homeserverUrl
            addListeners(
                listenFor(MRoomMessage::class) { event, roomId ->
                    this.getJoinedRoomFutureById(roomId)?.sendTextMessage(event.sender)
                },
                object : ListenerAdapter(receivePastEvents = true) {
                    override suspend fun onRoomInvite(event: RoomInviteEvent) { event.invitedRoomActions.join() }
                }
            )
            afterInitialSync { phone ->
                println("Inital sync!")
                //phone.getJoinedRoomFutures().map { it.receive() }.forEach { phone.getJoinedRoomFutureById(roomId)?.sendTextMessage(it.name) }
                //phone.stop()
            }
        }
        println("created dialphone")
        val job = phone.syncAndReturnJob()
        phone.addListener(MessageListener(false) {
                println(it.message.body)
                assertEquals(testString, it.message.body)
                it.message.redact("Test")
                phone.stop()
            }
        )
        println("Sending msg")
        val room: RoomFuture = phone.getJoinedRoomFutureById(roomId) ?: error("Room not found")

        // Test room info
        assertEquals("[Demo] Manuell", room.receive().name)
        assertEquals("@mtorials:mtorials.de", phone.getUserById("@mtorials:mtorials.de")?.id)

        room.sendTextMessage(testString)
        job.join()
    }

    suspend fun botTest(config: Config) {
        val phone = DialPhone {
            withToken(config.token)
            homeserverUrl = config.homeserverUrl
            bot {
                commandPrefix = "&"
                generateHelp()
                commands(
                    Command("ping") { params ->
                        this answer params[0]
                    }
                )
            }
        }
        phone.sync()
    }
}