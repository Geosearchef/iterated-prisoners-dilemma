package game

import Message
import ServerLoginMessage
import util.math.Rectangle

object Game {

    val someGameObject = Rectangle(200.0, 200.0, 300.0, 200.0)

    fun onServerMessage(message: Message) {
        if(message is ServerLoginMessage) {
            console.log("Successfully logged in")
        }
    }

    fun init() {

    }
}