import kotlinx.serialization.Serializable

@Serializable
data class GameInfo(val seats: Array<SeatInfo>)
@Serializable
data class SeatInfo(val id: Int, val color: String)