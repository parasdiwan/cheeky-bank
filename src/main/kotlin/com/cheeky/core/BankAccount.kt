package com.cheeky.core

import java.util.concurrent.atomic.AtomicReference

class BankAccount (
    internal val userId: String,
    internal val currency: String
): CheekyEntity() {

    private var balance: AtomicReference<Double> = AtomicReference(0.0)

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
            balance.compareAndSet(fetchedBalance, fetchedBalance - amount)
            destinationAccount.addAmount(amount)
        }
    }
}