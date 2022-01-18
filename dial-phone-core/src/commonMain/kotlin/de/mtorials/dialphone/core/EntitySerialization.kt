package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
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