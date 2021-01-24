package api

import spark.Spark.get

object Api {

    fun init() {
        get("/someRoute") { req, res ->
            "Hello World from someRoute"
        }
    }

}