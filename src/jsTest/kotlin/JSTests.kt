import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlin.test.Test

class JSTests {
    suspend fun basicTestJS() {
        println("OK")
        IntegrationTests().basicTestWithToken(Config("", ""))
    }
}