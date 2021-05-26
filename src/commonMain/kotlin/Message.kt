import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
sealed class Message() {
    companion object {
        fun fromJson(json: String) = Json.decodeFromString<Message>(json)
    }
    fun toJson() = Json.encodeToString(this)
}

@Serializable
data class ClientLoginMessage(val username: String, val authToken: String) : Message()

@Serializable
data class ServerLoginMessage(val gameInfo: GameInfo) : Message()

@Serializable
data class ClientEchoReplyMessage(val serverTimestamp: Long) : Message()

@Serializable
data class ServerEchoRequestMessage(val serverTimestamp: Long) : Message()

@Serializable
data class PlayerListInfoMessage(val players: Array<String>) : Message()

@Serializable
data class TimerInfoMessage(val timeLeft: Long) : Message()

@Serializable
data class OwnScoreInfoMessage(val score: Int) : Message()

@Serializable
data class AllScoresInfoMessage(val scores: Map<String, Int>) : Message()

@Serializable
data class RoundInfoMessage(val round: Int) : Message()
