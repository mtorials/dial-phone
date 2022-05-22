package de.mtorials.dialphone.olmmachine.bindings

data class MegolmV1BackupKey(
    var publicKey: String,
    var signatures: Map<String, Map<String, String>>,
    var passphraseInfo: PassphraseInfo?,
    var backupAlgorithm: String
)