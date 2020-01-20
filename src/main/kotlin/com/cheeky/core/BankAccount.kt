package com.cheeky.core

import java.util.*

class BankAccount (
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

    override fun copy(updateTime: Date, versionNumber: String): CheekyEntity {
        val bankAccount = BankAccount(userId, currency)
        bankAccount.balance = balance
        bankAccount.id = id
        bankAccount.versionNumber = versionNumber
        bankAccount.updateTime = updateTime
        return bankAccount
    }
}