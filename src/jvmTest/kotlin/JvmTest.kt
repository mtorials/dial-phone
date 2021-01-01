import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import java.io.File

class JvmTest {

    private val config: Config = Json { ignoreUnknownKeys = true }.decodeFromString(File("config.json").readText())

    @Test
    fun test() {
        runBlocking {
            CoreTests().basicTestWithToken(config)
        }
    }
}