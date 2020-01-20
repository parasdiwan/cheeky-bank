package com.cheeky.core

import java.util.Date

class Transaction (
    id: String,
    internal val sourceAccountId: String,
    internal val destinationAccountId: String,
    internal val userId: String,
    internal val amount: Double
): CheekyEntity(id, Date(), Date()) {
    var status: String = "Initiated"
        private set

    private lateinit var completionTime: Date

    fun completed() {
        status = "Completed"
        completionTime = Date()
    }
}