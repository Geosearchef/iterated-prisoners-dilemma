package game

import GameInfo
import Message
import ServerLoginMessage

object Game {

    lateinit var gameInfo: GameInfo

    fun onServerMessage(message: Message) {
        if(message is ServerLoginMessage) {
            console.log("Successfully logged in, got game info, table with ${message.gameInfo.seats.size}")

            gameInfo = message.gameInfo
            SeatsView.init()
        }
    }

    fun init() {

    }
}