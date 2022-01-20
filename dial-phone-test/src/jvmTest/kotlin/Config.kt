import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val homeserverUrl: String,
    val token: String
)