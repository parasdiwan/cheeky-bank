package com.cheeky

import java.util.Date

class BankAccount (
    val id: String,
    val creationTime: Date,
    var updateTime: Date,
    val userId: String,
    var balance: Double,
    val currency: String
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