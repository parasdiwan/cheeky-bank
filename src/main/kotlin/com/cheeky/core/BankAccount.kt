package com.cheeky.core

import java.util.Date

class BankAccount (
    id: String,
    internal val userId: String,
    internal val currency: String
): CheekyEntity() {

    private var balance: Double = 0.0

    fun isAmountDeductible(amount: Double): Boolean {
        return balance >= amount
    }

    fun deductAmount(amount: Double) {
        balance = balance - amount
    }

    fun addAmount(amount: Double) {
        balance = balance + amount
    }

    internal fun getBalance(): Double {
        return balance
    }
}