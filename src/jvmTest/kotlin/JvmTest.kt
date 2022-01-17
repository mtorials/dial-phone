import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.DialPhoneBuilder
import de.mtorials.dialphone.authentication.Login
import io.ktor.util.date.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import java.nio.file.attribute.UserPrincipalNotFoundException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class JvmTest {

    lateinit var dialPhone: DialPhone
    private val roomName: String = "this is a room name"

    @Before
    fun `register and login as user`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                isUser(TEST_USER, TEST_PWD, true)
            }
            // TODO remove rate limit in synapse
            delay(1000)
            dialPhone = DialPhone(HOMESERVER_URL) {
                isUser(TEST_USER, TEST_PWD)
            }
        }
    }

    @Test
    fun `check guest access`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                isGuest()
            }
        }
    }

    @Test
    fun `create room`() {
        runBlocking {
            // TODO
        }
    }

    companion object {
        const val HOMESERVER_URL = "http://localhost:8008"
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}