package de.mtorials.dialphone.core.cache

interface PhoneCache {
    val state: StateCache
    val timeline: TimelineCache?
}