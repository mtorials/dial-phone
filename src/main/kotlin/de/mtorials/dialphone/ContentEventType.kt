package de.mtorials.dialphone

import de.mtorials.dialphone.mevents.MatrixEvent
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class ContentEventType(val type: KClass<out MatrixEvent>)