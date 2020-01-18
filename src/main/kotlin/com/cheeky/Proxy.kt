package com.cheeky

import com.cheeky.di.DependencyManager
import io.javalin.Javalin

fun main() {

    DependencyManager("com.cheeky.core").initialize()
    val app = Javalin
        .create()
        .start(8000)

    app.get("/ping")
        { handler -> handler.result("pong") }

}