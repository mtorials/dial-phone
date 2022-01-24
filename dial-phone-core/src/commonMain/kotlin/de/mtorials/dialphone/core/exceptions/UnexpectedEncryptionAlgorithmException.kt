package de.mtorials.dialphone.core.exceptions

class UnexpectedEncryptionAlgorithmException(eventType: String) :
    RuntimeException("Found unexpected algorithm while trying to decrypt event of type $eventType.")