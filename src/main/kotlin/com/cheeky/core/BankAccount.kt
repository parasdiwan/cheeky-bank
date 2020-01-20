package com.cheeky.core

import com.google.common.util.concurrent.AtomicDouble
import java.util.function.BinaryOperator

class BankAccount (
    internal val userId: String,
    internal val currency: String
): CheekyEntity() {

    private var balance: AtomicDouble = AtomicDouble(0.0)

    fun isAmountDeductible(amount: Double): Boolean {
        return balance.get() >= amount
    }

    fun addAmount(amount: Double) {
        val fetchedBalance = getBalance()
        balance.compareAndSet(fetchedBalance, fetchedBalance + amount)
    }

    internal fun getBalance(): Double {
        return balance.get()
    }

    fun transferAmount(amount: Double, destinationAccount: BankAccount) {
        val fetchedBalance = getBalance()
        if (fetchedBalance >= amount) {
            val isSuccess = balance.compareAndSet(fetchedBalance, fetchedBalance - amount)
            destinationAccount.addAmount(amount)
        }
    }
}