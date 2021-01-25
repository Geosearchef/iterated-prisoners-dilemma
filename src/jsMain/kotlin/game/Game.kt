package game

import Message
import ServerLoginMessage

object Game {


    fun onServerMessage(message: Message) {
        if(message is ServerLoginMessage) {
            console.log("Successfully logged in")
        }
    }

    fun init() {

    }
}