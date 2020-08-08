package de.mtorials.dialphone

import net.micromes.makocommons.mevents.MatrixEvent
import kotlin.reflect.KClass

/**
 * This annotation links an event content class to its event class
 */
@Target(AnnotationTarget.CLASS)
annotation class ContentEventType(val type: KClass<out MatrixEvent>)