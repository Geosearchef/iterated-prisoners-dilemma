package game.players

import IterPriOptions
import Message
import game.GameManager
import game.TaskProcessor
import game.TaskProcessor.verifyTaskThread
import org.eclipse.jetty.websocket.api.Session
import util.Util
import websocket.WebsocketServer.getRemoteHostAddress

object PlayerManager {
    val log = Util.logger()

    val players: MutableList<Player> = ArrayList()

    fun addPlayer(player: Player) { // broadcast to all others only happens on join / leave seat
        verifyTaskThread()
        players.add(player)

        GameManager.onPlayerInitialConnect(player)
    }

    fun removePlayer(player: Player) {
        verifyTaskThread()
        players.remove(player)

        TaskProcessor.addTask(player) {
            GameManager.onPlayerDisconnect(player)
        }
    }

    fun broadcast(message: Message) {
        TaskProcessor.addTask {
            players.forEach {
                it.send(message)
            }
        }
    }

    fun getPlayerBySession(session: Session): Player? = players.find { it.session == session }

    fun attemptLogin(username: String, authToken: String, session: Session): Boolean {
        if(username.isBlank()) {
            log.info("${session.getRemoteHostAddress()} attempted to use blank username")
            return false
        }

        if(IterPriOptions.AUTH_ENABLED && Util.generateAuthCode(username) != authToken) {
            log.info("${session.getRemoteHostAddress()} attempted to use invalid auth token")
            return false
        }

        if(players.none { it.username.equals(username, ignoreCase = true) }) {
            log.info("${session.getRemoteHostAddress()} logged in as $username")
            TaskProcessor.addTask { addPlayer(Player(username, session)) }
            return true
        } else {
            log.info("${session.getRemoteHostAddress()} attempted to grab already taken username $username, disconnecting...")
            return false
        }
    }

    fun onSessionDisconnected(session: Session) {
        TaskProcessor.addTask {
            players.find { it.session == session }?.let {
                log.info("${it.username}(${session.getRemoteHostAddress()}) disconnected")
                removePlayer(it)
            }
        }
    }
}