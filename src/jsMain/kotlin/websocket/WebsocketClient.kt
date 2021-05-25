package websocket

import ClientEchoReplyMessage
import ClientLoginMessage
import IterPriOptions
import Message
import ServerEchoRequestMessage
import game.Game
import kotlinx.browser.window
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.url.URLSearchParams

object WebsocketClient {

    lateinit var socket: WebSocket

    fun send(message: Message) {
        socket.send(message.toJson())
    }

    fun onSocketMessage(event: MessageEvent) {
        val message = Message.fromJson(event.data.toString())

        if(message is ServerEchoRequestMessage) {
            send(ClientEchoReplyMessage(message.serverTimestamp))
            return
        }

        Game.onServerMessage(message)
    }

    fun onSocketClose() {
        console.log("Connection to websocket lost")
        window.location.href = "/?connectionLost=true";
    }

    fun onSocketOpen() {
        console.log("Connected to websocket, logging in...")
        val username = URLSearchParams(window.location.search).get("username") // TODO: put into central location (game / state)
        val authToken = URLSearchParams(window.location.search).get("authToken")
        username?.let { authToken?.let { send(ClientLoginMessage(username, authToken)) } }
    }

    fun init() {
        val websocketUrl = "ws://${window.location.hostname}:${window.location.port}${IterPriOptions.WEBSOCKET_ROUTE}"
        console.log("Connecting to web socket at $websocketUrl")
        socket = WebSocket(websocketUrl)

        socket.onopen = { onSocketOpen() }
        socket.onclose = { onSocketClose() }
        socket.onmessage = { event: MessageEvent -> onSocketMessage(event) }
        socket.onerror = { console.error("Websocket error: ", it) }
    }
}