package game.players

import Message
import org.eclipse.jetty.websocket.api.Session
import websocket.WebsocketServer
import java.time.Instant

class Player(val username: String, val session: Session) {

    var latency: Int = Integer.MAX_VALUE
        set(value) {
            field = value
            lastEchoReply = Instant.now()
        }
    var lastEchoReply: Instant = Instant.now()

    var seat: Int? = null

    fun send(message: Message) {
        WebsocketServer.send(this.session, message)
    }
}