package game.players

import org.eclipse.jetty.websocket.api.Session
import java.time.Instant

class Player(val username: String, val session: Session) {

    var latency: Int = Integer.MAX_VALUE
        set(value) {
            field = value
            lastEchoReply = Instant.now()
        }
    var lastEchoReply: Instant = Instant.now()
}