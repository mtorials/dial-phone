package de.mtorials.dialphone.core.exceptions

class UnableToSendException(cause: Exception) : RuntimeException("Unable to send messages", cause = cause)