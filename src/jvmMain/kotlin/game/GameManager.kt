package game

import ClientLoginMessage
import Message
import game.players.Player

object GameManager {

    // single threaded
    fun init() {

    }


    fun onMessageReceived(message: Message, player: Player) {
        TaskProcessor.addTask(player) {
            when(message) {
                is ClientLoginMessage -> {// TODO: remove, processed by websocket server

                }
            }
        }
    }
}