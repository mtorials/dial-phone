import io.ktor.util.date.*
import kotlin.random.Random

object Helper {
    val random = Random(getTimeMillis())
    private val abc = listOf("A", "B", "C", "D", "E", "F", "G", " ", "+", "-", "!", "?", ".", "1", "2", "4")
    fun getRandomName(length: Int = 10) = (0..length).map { abc[random.nextInt(0, abc.size - 1)] }
        .joinToString { "" }
}