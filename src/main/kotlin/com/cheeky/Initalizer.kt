package com.cheeky

import com.cheeky.di.DependencyManager
import io.javalin.Javalin

fun main() {

    val app = Javalin
        .create()
        .start(8000)

    val dependencyManager = DependencyManager("com.cheeky.core")
    dependencyManager.initialize()
    dependencyManager.addUnit(app)
}