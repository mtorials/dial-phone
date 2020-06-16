package de.mtorials.dial.mevents

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class EventType(val typename: String)