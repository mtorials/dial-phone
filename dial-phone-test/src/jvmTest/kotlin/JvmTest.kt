import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


class JvmTest {

    private val text = Helper.getRandomName(50)

    lateinit var dialPhone: DialPhone
    lateinit var messageListener: ListenerAdapter

    @Before
    fun `register and login as user`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                asUser(TEST_USER, TEST_PWD, true)
            }
            // TODO remove rate limit in synapse
            delay(1000)
            dialPhone = DialPhone(HOMESERVER_URL) {
                asUser(TEST_USER, TEST_PWD)
                addListeners(ListenerAdapter {
                    onRoomMessageReceived {
                        assertEquals(it.message.body, text)
                    }
                })
            }
        }
    }

    @Test
    fun `check guest access`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                asGuest()
            }
        }
    }

    @Test
    fun `create room`() {
        runBlocking {
            delay(1000)
            val name = Helper.getRandomName(10)
            val room = dialPhone.createRoom(name) {
                topic = "This is a room topic"
            }
            assertEquals(room.receive().name, name)
            room.sendTextMessage(text)
            // wait for the round trip
            delay(1000)
        }
    }

    companion object {
        const val HOMESERVER_URL = "http://localhost:8008"
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}