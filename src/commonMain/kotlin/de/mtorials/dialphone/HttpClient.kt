package de.mtorials.dialphone

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import net.mt32.makocommons.EventSerialization

object HttpClient {
    val client = HttpClient() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(EventSerialization.matrixFormat)
        }
    }
}