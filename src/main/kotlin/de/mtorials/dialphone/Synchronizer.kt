package de.mtorials.dialphone

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import de.mtorials.dialphone.listener.Listener
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.responses.SyncResponse
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

class Synchronizer(
    private val listeners: MutableList<Listener>,
    private val phone: DialPhone,
    subTypes: Array<KClass<out MatrixEvent>>
) {

    private val mapper = jacksonObjectMapper()
    private var lastTimeBatch: String? = null

    init {
        subTypes.forEach { mapper.registerSubtypes(it.java) }
        runBlocking {
            // Do not get old events
            lastTimeBatch = getSyncResponse().nextBatch
        }
    }

    fun addListener(listener: Listener) = listeners.add(listener)

    suspend fun sync() {
        while(true) {
            val a = getSyncResponse()
            lastTimeBatch = a.nextBatch
            val roomEventsByID = a.roomSync.join
            roomEventsByID.forEach { (roomID, roomEvents) ->
                roomEvents.timeline.events.forEach { event ->
                    println(
                        "An Event of Type ${event.javaClass.name} occurred in Room with ID $roomID \n" +
                        "The content is ${event.content}."
                    )
                    listeners.forEach { it.onRoomEvent(event, roomID, phone) }
                }
            }
        }
    }

    private suspend fun getSyncResponse() : SyncResponse {
        val parameters: MutableList<Pair<String, String>> = mutableListOf()
        if (lastTimeBatch != null) parameters.add(Pair("since", lastTimeBatch as String))
        parameters.add(Pair("timeout", DialPhone.TIMEOUT))
        parameters.add(Pair("full_state", "false"))
        val req = Fuel.get(phone.homeServerURL + DialPhone.MATRIX_PATH + "sync", parameters)
        req["Authorization"] = "Bearer ${phone.token}"
        req
            .awaitStringResponseResult().third
            .fold<Unit>(
                { data -> return@getSyncResponse mapper.readValue(data) },
                { _ -> throw RuntimeException("Problem Syncing") }
            )
        throw RuntimeException("Dont know")
    }
}