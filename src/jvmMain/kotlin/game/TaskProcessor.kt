package game

import game.players.Player
import util.Util.logger
import websocket.WebsocketServer
import java.util.*

object TaskProcessor {
    private val log = logger()

    private val workerThread = Thread(TaskProcessor::process).apply { start() }
    private val taskQueue: Queue<Task> = LinkedList()
    private var stopRequested = false

    fun process() {
        while(! synchronized(workerThread) { stopRequested }) {
            while(true) {
                val task: Task? = synchronized(taskQueue) { taskQueue.poll() }

                if(task != null) {
                    try {
                        task.runnable.run()
                    } catch(e: Exception) {
                        log.warn("Error while processing task from ${(task.source as? Player?)?.username ?: "server"}")
                    }
                } else {
                    break
                }
            }

            try { Thread.sleep(5) } catch(e: InterruptedException) {}
        }

        WebsocketServer.log.info("Terminating message processor")
    }

    fun addTask(runnable: Runnable) = addTask(Task(null, runnable))
    fun addTask(source: Any?, runnable: Runnable) = addTask(Task(source, runnable))
    fun addTask(task: Task) {
        synchronized(taskQueue) {
            taskQueue.add(task)
        }
    }

    fun requestStop() {
        synchronized(workerThread) {
            stopRequested = true
            workerThread.interrupt()
        }
    }

    data class Task(val source: Any?, val runnable: Runnable)
}