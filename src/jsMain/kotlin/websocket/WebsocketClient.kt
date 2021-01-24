package websocket

import CardSimulatorOptions
import ClientLoginMessage
import Message
import kotlinx.browser.window
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event
import org.w3c.dom.url.URLSearchParams

object WebsocketClient {

    lateinit var socket: WebSocket

    fun send(message: Message) {
        socket.send(message.toJson())
    }

    fun onSocketMessage(event: Event) {
        //todo
    }

    fun onSocketClose(event: Event) {
        window.location.href = "/?connectionLost=true";
    }

    fun onSocketOpen(event: Event) {
        val username = URLSearchParams(window.location.search).get("username") // TODO: put into central location (game / state)
        username?.let { send(ClientLoginMessage(username)) }
    }

    fun init() {
        val websocketUrl = "ws://${window.location.hostname}:${window.location.port}${CardSimulatorOptions.WEBSOCKET_ROUTE}"
        console.log("Connecting to web socket at $websocketUrl")
        socket = WebSocket(websocketUrl)

        socket.onopen = WebsocketClient::onSocketOpen
        socket.onclose = WebsocketClient::onSocketClose
        socket.onmessage = WebsocketClient::onSocketMessage
        socket.onerror = { console.error("Websocket error: ", it) }
    }
}