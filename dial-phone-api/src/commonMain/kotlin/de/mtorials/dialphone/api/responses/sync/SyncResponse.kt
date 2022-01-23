package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import de.mtorials.dialphone.api.model.mevents.presence.MPresence

@Serializable
class SyncResponse(
    val presence: SyncPresence? = null,
    val rooms: SyncRooms? = null,
    @SerialName("next_batch")
    val nextBatch: String,
    @SerialName("to_device")
    val toDevice: ToDevice? = null,
    @SerialName("device_one_time_keys_count")
    val deviceOneTimeKeysCount: Map<String, Int>? = null,
    @SerialName("devices_list")
    val devicesList: DevicesList? = null,
    @SerialName("account_data")
    val accountData: AccountData? = null,
) {
    
    @Serializable
    class SyncRooms(
        val join: Map<String, JoinedRoomResponse> = mutableMapOf(),
        val invite: Map<String, InvitedRoomResponse> = mutableMapOf(),
        val leave: Map<String, LeftRoomResponse> = mutableMapOf(),
        val knock: Map<String, KnockedRoomResponse> = mutableMapOf(),
    )
    @Serializable
    class SyncPresence(
        val events: List<MPresence>
    )
    @Serializable
    data class DevicesList(
        val changed: List<String>,
        val left: List<String>,
    )
    @Serializable
    data class ToDevice(
        val events: List<MatrixEvent> = emptyList()
    )
}