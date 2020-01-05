package com.cheeky

import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(8000)
    app.get("/ping") { handler -> handler.result("pong") }
}