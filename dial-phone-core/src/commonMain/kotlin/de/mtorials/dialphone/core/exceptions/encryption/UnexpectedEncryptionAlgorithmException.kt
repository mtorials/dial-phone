package de.mtorials.dialphone.core.exceptions.encryption

class UnexpectedEncryptionAlgorithmException(eventType: String) :
    EncryptionException("Found unexpected algorithm while trying to decrypt event of type $eventType.")