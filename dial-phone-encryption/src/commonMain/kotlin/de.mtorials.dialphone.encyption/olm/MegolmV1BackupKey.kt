package de.mtorials.dialphone.encyption.olm

data class MegolmV1BackupKey(
    var publicKey: String,
    var signatures: Map<String, Map<String, String>>,
    var passphraseInfo: PassphraseInfo?,
    var backupAlgorithm: String
)