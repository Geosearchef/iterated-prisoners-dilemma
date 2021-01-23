package websocket

import CardSimulatorOptions
import ClientLoginMessage
import Message
import game.GameManager
import game.players.PlayerManager
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.StatusCode
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import spark.Spark.webSocket
import util.Util
import java.io.IOException
import java.time.Duration

@WebSocket
object WebsocketServer {
    val SOCKET_IDLE_TIMEOUT = Duration.ofMinutes(2)
    val log = Util.logger()

    val sessions: MutableList<Session> = ArrayList()

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        log.info("Session connected from ${session.getRemoteHostAddress()}") // TODO: switch to debug

        synchronized(sessions) {
            sessions.add(session)
        }
        session.idleTimeout = SOCKET_IDLE_TIMEOUT.seconds
    }

    @OnWebSocketClose
    fun onDisconnect(session: Session, statusCode: Int, reason: String) {
        log.info("Session disconnected: ${session.getRemoteHostAddress()} with status code $statusCode, due to $reason")
        PlayerManager.onSessionDisconnected(session)
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, json: String) {
        val message = Message.fromJson(json) // TODO: test crash

        if(message is ClientLoginMessage) {
            val success = PlayerManager.attemptLogin(message.username, session)
            if(!success) {
                session.close(StatusCode.PROTOCOL, "Username invalid or already taken")
                return
            }
        } else {
            val player = PlayerManager.getPlayerBySession(session)
            if(player != null) {
                GameManager.onMessageReceived(message, player)
            } else {
                log.warn("Received message from unauthenticated session ${session.getRemoteHostAddress()}, disconnecting")
                session.close(StatusCode.PROTOCOL, "Unauthenticated")
                return
            }
        }
    }

    @Throws(IOException::class)
    fun send(session: Session, message: String) {
        if(session.isOpen) {
            session.remote.sendStringByFuture(message) // alternatively bytes
        }
    }

    fun init() {
        webSocket(CardSimulatorOptions.WEBSOCKET_ROUTE, this)
    }

    fun Session.getRemoteHostAddress() = this.remoteAddress.address.hostAddress
}
