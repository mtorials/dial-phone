package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entities.User
import de.mtorials.dialphone.entities.entities.UserImpl
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