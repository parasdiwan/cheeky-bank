package com.cheeky.core

import com.cheeky.di.Inject
import io.javalin.Javalin

class Proxy @Inject public constructor (router: Javalin) {

    init {
        router.get("/ping")
        { handler -> handler.result("pong") }

//        router.post("/transfers")
//        { handler -> handler.req.}
    }
}