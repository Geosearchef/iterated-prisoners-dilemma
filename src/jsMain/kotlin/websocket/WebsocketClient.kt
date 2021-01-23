package websocket

import org.w3c.dom.WebSocket

object WebsocketClient {

    lateinit var socket: WebSocket

    fun init() {
        socket = WebSocket()
        // https://github.com/Geosearchef/rtsIO/blob/master/public/play/websocket.js
    }
}