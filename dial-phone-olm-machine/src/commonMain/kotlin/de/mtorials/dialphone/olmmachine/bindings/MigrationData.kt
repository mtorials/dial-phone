package de.mtorials.dialphone.olmmachine.bindings

data class MigrationData(
    var account: PickledAccount,
    var sessions: List<PickledSession>,
    var inboundGroupSessions: List<PickledInboundGroupSession>,
    var backupVersion: String?,
    var backupRecoveryKey: String?,
    var pickleKey: String,
    var crossSigning: CrossSigningKeyExport,
    var trackedUsers: List<String>
)