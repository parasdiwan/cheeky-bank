package com.cheeky.core

import com.cheeky.di.CheekyUnit
import com.cheeky.di.Inject
import com.google.gson.Gson
import io.javalin.Javalin

@CheekyUnit
class Proxy @Inject public constructor (
    router: Javalin,
    transactionService: TransactionService
) {

    init {
        router.get("/ping")
        { handler -> handler.result("pong") }

        router.post("/transfers")
        { handler ->
            val body = handler.body()
            val request = Gson().fromJson(body, TransferRequestVM::class.java)
            transactionService.transferMoney(
                request.userId,
                request.sourceAccountId,
                request.destAccountId,
                request.amount
            )
        }
    }
}

data class TransferRequestVM (
    val userId: String,
    val sourceAccountId: String,
    val destAccountId: String,
    val amount: Double
){
}