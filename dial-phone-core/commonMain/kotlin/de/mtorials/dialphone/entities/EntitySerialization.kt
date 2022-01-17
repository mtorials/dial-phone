package de.mtorials.dialphone.entities

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EntitySerialization {
    val serializersModule = SerializersModule {
        polymorphic(User::class) {
            subclass(UserImpl::class)
        }
    }
}