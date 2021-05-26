package game

import GameInfo
import Message
import PlayerListInfoMessage
import SeatInfo
import TimerInfoMessage
import game.GameManager.GameState.WAITING_FOR_PLAYERS
import game.TaskProcessor.verifyTaskThread
import game.players.Player
import game.players.PlayerManager
import game.players.PlayerManager.broadcast
import util.Util.logger
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

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
    var nextTimerEnd = Instant.now() + Duration.ofHours(1)

    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()


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

    fun startGame() {
        // todo: read from stdin
    }

    fun setTimer(duration: Duration) {
        nextTimerEnd = Instant.now() + duration

        TimerInfoMessage(duration.toMillis())
        broadcast(TimerInfoMessage(duration.toMillis()))
    }

    fun onPlayerInitialConnect(connectingPlayer: Player) {
        verifyTaskThread()

        broadcast(PlayerListInfoMessage(PlayerManager.players.map { it.username }.toTypedArray()))
        connectingPlayer.send(TimerInfoMessage(Duration.between(Instant.now(), nextTimerEnd).toMillis()))
    }

    fun onPlayerDisconnect(player: Player) {
        verifyTaskThread()

        // TODO: remove from game, auto select answers
        broadcast(PlayerListInfoMessage(PlayerManager.players.map { it.username }.toTypedArray()))
    }
}