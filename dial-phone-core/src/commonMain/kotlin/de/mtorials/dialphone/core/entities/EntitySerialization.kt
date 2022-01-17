package de.mtorials.dialphone.core.entities

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EntitySerialization {
    val serializersModule = SerializersModule {
        polymorphic(de.mtorials.dialphone.core.entities.User::class) {
            subclass(de.mtorials.dialphone.core.entities.UserImpl::class)
        }
    }
}