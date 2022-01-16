import de.mtorials.dialphone.DialPhone
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals


class JvmTest {

    //private val config: Config = Json { ignoreUnknownKeys = true }.decodeFromString(File("config.json").readText())

//    @Container
//    val homeserverContainer : GenericContainer<Nothing> = GenericContainer<Nothing>(SYNAPSE_IMAGE).apply {
//            waitingFor(object : WaitStrategy {
//                override fun waitUntilReady(waitStrategyTarget: WaitStrategyTarget?) {
//                    runBlocking {
//                        delay(10000)
//                    }
//                }
//
//                override fun withStartupTimeout(startupTimeout: Duration?): WaitStrategy {
//                    return this
//                }
//            })
//        }
//        .withExposedPorts(8008)

//    @Container
//    val postgres = PostgreSQLContainer("postgres")

//    @Container
//    val elementWeb : GenericContainer<Nothing> = GenericContainer<Nothing>(DockerImageName.parse("kalaksi/element-web:latest"))
//        .withExposedPorts(80)

    lateinit var dialPhone: DialPhone

    private fun setDialPhone() {
//        println(homeserverContainer.host)
//        homeserverContainer.apply {
//            execInContainer("register_new_matrix_user -c /config/homeserver.yaml -p $TEST_PWD -u $TEST_USER --no-admin http://localhost:8008")
//            execInContainer("register_new_matrix_user -c /config/homeserver.yaml -p test -u test --no-admin http://localhost:8008")
//        }
        runBlocking {
            dialPhone = DialPhone {
                homeserverUrl = "http://localhost:8008/"
            }
        }
        GlobalScope.launch {
            dialPhone.sync()
        }
    }

    @Test
    fun `send and receive`() {
        setDialPhone()
        assertEquals("Hi", "Hi")
    }

    companion object {
//        val SYNAPSE_IMAGE: DockerImageName = DockerImageName.parse("matrixdotorg/synapse:latest")
//        val POSTGRES_IMAGE: DockerImageName = DockerImageName.parse("postgres:latest")
//        val ELEMENT_IMAGE: DockerImageName = DockerImageName.parse("kalaksi/element-web:latest")
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}