package game

import ClientLoginMessage
import Message
import SeatInfo
import game.players.Player

object GameManager {

    val gameInfo = GameInfo(arrayOf(
        SeatInfo(0, "#ED6B4C"),
        SeatInfo(1, "#53C2E0"),
        SeatInfo(2, "#F0C659"),
        SeatInfo(3, "#AB4FF7"),
        SeatInfo(4, "#74F74F"),
    ))

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