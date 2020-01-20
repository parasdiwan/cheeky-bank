package com.cheeky.core

import com.google.common.util.concurrent.AtomicDouble
import java.lang.IllegalArgumentException
import java.util.*

class BankAccount (
    internal val token: String,
    internal val currency: String
): CheekyEntity() {

    private var balance: AtomicDouble = AtomicDouble(0.0)

    fun isAmountDeductible(amount: Double): Boolean {
        return balance.get() >= amount
    }

    fun deductAmount(amount: Double) {
        val fetchedBalance = balance.get()
        if (amount > fetchedBalance) {
            throw IllegalArgumentException("Amount cannot be more than the balance.")
        }
        balance.compareAndSet(fetchedBalance, fetchedBalance - amount)
    }

    fun addAmount(amount: Double) {
        val fetchedBalance = balance.get()
        balance.compareAndSet(fetchedBalance, fetchedBalance + amount)
    }

    internal fun getBalance(): Double {
        return balance.get()
    }

    override fun copy(updateTime: Date, versionNumber: String): CheekyEntity {
        val bankAccount = BankAccount(token, currency)
        bankAccount.balance = balance
        bankAccount.id = id
        bankAccount.versionNumber = versionNumber
        bankAccount.updateTime = updateTime
        return bankAccount
    }
}