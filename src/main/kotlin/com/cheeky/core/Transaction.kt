package com.cheeky.core

import java.util.Date

class Transaction (
    internal val id: String,
    internal val creationTime: Date,
    internal var updateTime: Date,
    internal val sourceAccountId: String,
    internal val destinationAccountId: String,
    internal val userId: String,
    internal val amount: Double
) {
    private var status: String = "Initiated"
    private lateinit var completionTime: Date

    fun completed() {
        status = "Completed"
        completionTime = Date()
    }
}