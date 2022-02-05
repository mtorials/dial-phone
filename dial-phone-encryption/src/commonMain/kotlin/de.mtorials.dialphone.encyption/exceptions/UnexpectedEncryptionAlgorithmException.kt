package de.mtorials.dialphone.encyption.exceptions

class UnexpectedEncryptionAlgorithmException(eventType: String) :
    EncryptionException("Found unexpected algorithm while trying to decrypt event of type $eventType.")