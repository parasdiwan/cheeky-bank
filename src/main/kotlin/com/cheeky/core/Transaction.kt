package com.cheeky.core

import java.util.Date

class Transaction (
    internal val sourceAccountId: String,
    internal val destinationAccountId: String,
    internal val userId: String,
    internal val amount: Double
): CheekyEntity() {
    var status: String = "Initiated"
        private set

    private lateinit var completionTime: Date

    fun completed() {
        status = "Completed"
        completionTime = Date()
    }

    override fun copy(): Transaction {
        val transaction = Transaction(sourceAccountId, destinationAccountId, userId, amount)
        transaction.id = id
        transaction.versionNumber = versionNumber
        transaction.updateTime = updateTime
        return transaction

    }
}