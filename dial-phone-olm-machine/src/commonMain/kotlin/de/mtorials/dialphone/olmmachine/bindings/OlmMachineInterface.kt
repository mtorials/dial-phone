package de.mtorials.dialphone.olmmachine.bindings

interface OlmMachineInterface {

    fun identityKeys(): Map<String, String>

    fun userId(): String

    fun deviceId(): String

    fun receiveSyncChanges(events: String, deviceChanges: DeviceLists, keyCounts: Map<String, Int>, unusedFallbackKeys: List<String>?): String

    fun outgoingRequests(): List<Request>

    fun markRequestAsSent(requestId: String, requestType: RequestType, response: String)

    fun decryptRoomEvent(event: String, roomId: String): DecryptedEvent

    fun encrypt(roomId: String, eventType: String, content: String): String

    fun getIdentity(userId: String): UserIdentity?

    fun verifyIdentity(userId: String): SignatureUploadRequest

    fun getDevice(userId: String, deviceId: String): Device?

    fun markDeviceAsTrusted(userId: String, deviceId: String)

    fun verifyDevice(userId: String, deviceId: String): SignatureUploadRequest

    fun getUserDevices(userId: String): List<Device>

    fun isUserTracked(userId: String): Boolean

    fun updateTrackedUsers(users: List<String>)

    fun getMissingSessions(users: List<String>): Request?

    fun shareRoomKey(roomId: String, users: List<String>): List<Request>

    fun receiveUnencryptedVerificationEvent(event: String, roomId: String)

    fun getVerificationRequests(userId: String): List<VerificationRequest>

    fun getVerificationRequest(userId: String, flowId: String): VerificationRequest?

    fun getVerification(userId: String, flowId: String): Verification?

    fun requestVerification(userId: String, roomId: String, eventId: String, methods: List<String>): VerificationRequest?

    fun verificationRequestContent(userId: String, methods: List<String>): String?

    fun requestSelfVerification(methods: List<String>): RequestVerificationResult?

    fun requestVerificationWithDevice(userId: String, deviceId: String, methods: List<String>): RequestVerificationResult?

    fun acceptVerificationRequest(userId: String, flowId: String, methods: List<String>): OutgoingVerificationRequest?

    fun confirmVerification(userId: String, flowId: String): ConfirmVerificationResult?

    fun cancelVerification(userId: String, flowId: String, cancelCode: String): OutgoingVerificationRequest?

    fun startSasWithDevice(userId: String, deviceId: String): StartSasResult?

    fun startSasVerification(userId: String, flowId: String): StartSasResult?

    fun acceptSasVerification(userId: String, flowId: String): OutgoingVerificationRequest?

    fun getEmojiIndex(userId: String, flowId: String): List<Int>?

    fun getDecimals(userId: String, flowId: String): List<Int>?

    fun startQrVerification(userId: String, flowId: String): QrCode?

    fun scanQrCode(userId: String, flowId: String, data: String): ScanResult?

    fun generateQrCode(userId: String, flowId: String): String?

    fun requestRoomKey(event: String, roomId: String): KeyRequestPair

    fun exportKeys(passphrase: String, rounds: Int): String

    fun importKeys(keys: String, passphrase: String, progressListener: ProgressListener): KeysImportResult

    fun importDecryptedKeys(keys: String, progressListener: ProgressListener): KeysImportResult

    fun discardRoomKey(roomId: String)

    fun crossSigningStatus(): CrossSigningStatus

    fun bootstrapCrossSigning(): BootstrapCrossSigningResult

    fun exportCrossSigningKeys(): CrossSigningKeyExport?

    fun importCrossSigningKeys(export: CrossSigningKeyExport)

    fun isIdentityVerified(userId: String): Boolean

    fun sign(message: String): Map<String, Map<String, String>>

    fun enableBackupV1(key: MegolmV1BackupKey, version: String)

    fun disableBackup()

    fun backupRoomKeys(): Request?

    fun saveRecoveryKey(key: String?, version: String?)

    fun roomKeyCounts(): RoomKeyCounts

    fun getBackupKeys(): BackupKeys?

    fun backupEnabled(): Boolean

    fun verifyBackup(authData: String): Boolean
}