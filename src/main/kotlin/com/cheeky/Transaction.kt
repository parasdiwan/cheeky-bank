package com.cheeky

import java.util.Date

class Transaction (
    private val id: String,
    private val creationTime: Date,
    private var updateTime: Date,
    private val sourceAccountId: String,
    private val destinationAccountId: String,
    private val userId: String
) {
    private var status: String = "Initiated"
    private lateinit var completionTime: Date

    fun completed() {
        status = "Completed"
        completionTime = Date()
    }
}