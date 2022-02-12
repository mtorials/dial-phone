package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
fun getEventTypeAsString(event: MatrixEvent) : String = event::class.serializer().descriptor.serialName
