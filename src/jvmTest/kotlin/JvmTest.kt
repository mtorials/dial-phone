import de.mtorials.dialphone.DialPhone
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class JvmTest {

    //private val config: Config = Json { ignoreUnknownKeys = true }.decodeFromString(File("config.json").readText())

    @Container
    val homeserverContainer = GenericContainer<Nothing>(DockerImageName.parse("matrixdotorg/synapse:latest")).apply {
        addExposedPorts(8008)
    }

    @Container
    val elementWeb = GenericContainer<Nothing>(DockerImageName.parse("kalaksi/element-web:latest")).apply {
        addExposedPorts(80)
    }

    lateinit var dialPhone: DialPhone

    fun before() {
//        homeserverContainer.start()
//        elementWeb.start()
//        homeserverContainer.apply {
//            execInContainer("register_new_matrix_user -c /config/homeserver.yaml -p $TEST_PWD -u $TEST_USER --no-admin http://localhost:8008")
//            execInContainer("register_new_matrix_user -c /config/homeserver.yaml -p test -u test --no-admin http://localhost:8008")
//        }
//        GlobalScope.launch {
//            dialPhone = DialPhone {
//                homeserverUrl = "http://" + homeserverContainer.host + ":8080"
//            }
//        }
//        GlobalScope.launch {
//            dialPhone.sync()
//        }
    }

    @Test
    fun `send and receive`() {
        assertEquals("Hi", "Hi")
    }

    companion object {
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}