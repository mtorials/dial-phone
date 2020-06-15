package de.mtorials.dialexample

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

val bridgedRoomIDAndServerIDChannelIDPair: MutableList<Pair<Pair<String, String>, String>> = mutableListOf()

fun main() {

    var listener : BridgeListener

    val phone = DialPhone(
        homeServerURL = config["homeServerUrl"] ?: throw Error(),
        token = config["matrixToken"] ?: throw Error()
    )

    val job1 = GlobalScope.launch { phone.sync() }
    runBlocking {
        val myRoom : Room = (phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
            ?: throw RuntimeException("Not Found")).receive()
        myRoom.members.forEach { member -> println(member.displayName) }
        println(myRoom.avatarUrl)
        println(myRoom.name)
    }
    runBlocking {
        job1.join()
    }
}

