package util

import IterPriOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


object Util {
    fun isRunningFromJar() = IterPriOptions.javaClass.getResource("IterPriOptions.class").toString().startsWith("jar")
    inline fun logger(): Logger = LoggerFactory.getLogger(Class.forName(Thread.currentThread().stackTrace[1].className))

    fun generateAuthCode(username: String): String {
        val digest = MessageDigest.getInstance("SHA-256");
        val hash = digest.digest((username + "thisisnotapyramid").toByteArray(charset = StandardCharsets.UTF_8))
        val authCode = String(hash).substring(0,6)
        return authCode
    }
}
