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


    private var _username: String? = null
    var username: String
        get() = _username ?: ""
        set(value) {
            _username = value
        }
}