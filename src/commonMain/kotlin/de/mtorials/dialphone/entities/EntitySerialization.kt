package de.mtorials.dialphone.entities

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EntitySerialization {
    val matrixFormat = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
        serializersModule = SerializersModule {
            polymorphic(User::class) {
                subclass(UserImpl::class)
            }
        }
    }
}