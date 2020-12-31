package de.mtorials.dpexample

import de.mtorials.dialphone.DialPhone
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test

@Serializable
data class Config(val homeserverUrl: String, val token: String)

class CoreTests {
    @Test
    fun testCore() {
        println("Test begins!")
    }
}