package util

import CardSimulatorOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object Util {
    fun isRunningFromJar() = CardSimulatorOptions.javaClass.getResource("CardsOptions.class").toString().startsWith("jar")
    inline fun logger(): Logger = LoggerFactory.getLogger(Class.forName(Thread.currentThread().stackTrace[1].className))
}
