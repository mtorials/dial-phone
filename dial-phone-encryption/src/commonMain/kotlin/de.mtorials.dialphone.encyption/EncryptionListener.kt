package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.responses.sync.SyncResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EncryptionListener(
    private val encryptionManager: EncryptionManager,
) : GenericListener<DialPhoneApi> {

    override fun onSyncResponse(syncResponse: SyncResponse, coroutineScope: CoroutineScope) {
        try {
            encryptionManager.handleEvent(
                events = syncResponse.toDevice?.events ?: emptyList(),
                syncResponse.deviceList,
                keyCounts = syncResponse.deviceOneTimeKeysCount ?: emptyMap()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        coroutineScope.launch { encryptionManager.update() }
    }
}