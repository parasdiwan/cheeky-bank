package com.cheeky.core

import java.util.Date

class BankAccount (
    internal val id: String,
    internal val creationTime: Date,
    internal var updateTime: Date,
    internal val userId: String,
    private var balance: Double,
    internal val currency: String
){
    fun isAmountDeductible(amount: Double): Boolean {
        return balance >= amount
    }

    fun deductAmount(amount: Double) {
        balance = balance - amount
    }

    fun addAmount(amount: Double) {
        balance = balance + amount
    }
}