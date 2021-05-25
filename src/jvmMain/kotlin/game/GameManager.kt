package game

import GameInfo
import Message
import PlayerListInfoMessage
import SeatInfo
import game.GameManager.GameState.WAITING_FOR_PLAYERS
import game.TaskProcessor.verifyTaskThread
import game.players.Player
import game.players.PlayerManager
import util.Util.logger

object GameManager {

    val log = logger()

    val gameInfo = GameInfo(
        arrayOf(
            SeatInfo(0, "#ED6B4C"),
            SeatInfo(1, "#53C2E0"),
            SeatInfo(2, "#F0C659"),
            SeatInfo(3, "#AB4FF7"),
            SeatInfo(4, "#74F74F"),
        )
    )

    enum class GameState {
        WAITING_FOR_PLAYERS, ROUND_IN_PROGRESS, WAITING_FOR_ROUND
    }

    val gameState = WAITING_FOR_PLAYERS



    // single threaded
    fun init() {

    }


    fun onMessageReceived(msg: Message, player: Player) {
        TaskProcessor.addTask(player) {
            when (msg) {
//                is ClientJoinSeatMessage -> {
//                    playerJoinSeat(player, msg.seatId)
//                }
            }
        }
    }

    fun onPlayerInitialConnect(connectingPlayer: Player) {
        verifyTaskThread()

        PlayerManager.broadcast(PlayerListInfoMessage(PlayerManager.players.map { it.username }.toTypedArray()))
    }

    fun onPlayerDisconnect(player: Player) {
        verifyTaskThread()

        // TODO: remove from game, auto select answers
        PlayerManager.broadcast(PlayerListInfoMessage(PlayerManager.players.map { it.username }.toTypedArray()))
    }
}