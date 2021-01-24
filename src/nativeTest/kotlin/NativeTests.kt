import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.utils.io.*
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import platform.linux.LIO_READ
import platform.linux.READ_IMPLIES_EXEC
import platform.linux.TD_READY
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import kotlin.test.Test

class NativeTests {

    private val config: Config = Json { ignoreUnknownKeys = true }.decodeFromString(readAllText("config.json"))

    @Test
    fun nativeTest() {
        runBlocking {
            IntegrationTests().basicTestWithToken(config)
        }
    }
}

fun readAllText(filePath: String): String {
    val returnBuffer = StringBuilder()
    // TODO does this work?
    val file = fopen(filePath, "r") ?:
        throw IllegalArgumentException("Cannot open input file $filePath")

    try {
        memScoped {
            val readBufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line = fgets(buffer, readBufferLength, file)?.toKString()
            while (line != null) {
                returnBuffer.append(line)
                line = fgets(buffer, readBufferLength, file)?.toKString()
            }
        }
    } finally {
        fclose(file)
    }

    return returnBuffer.toString()
}