package de.mtorials.dial.actions

import de.mtorials.dial.DialPhone
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class DialFuture<T>(
    val entityId: String,
    val phone: DialPhone
) {
    val requestObject = phone.requestObject
    abstract suspend fun complete() : T
    fun queue() {
        GlobalScope.launch {
            complete()
        }
    }
}