package com.cheeky.core

import com.cheeky.di.CheekyUnit
import com.cheeky.di.Inject
import java.lang.IllegalArgumentException
import java.util.UUID
import java.util.Date

@CheekyUnit
class TransactionService public @Inject constructor(
    private var transactions: TransactionRepository,
    private var accounts: BankAccountRepository
) {


    fun transferMoney(userId: String, sourceAccountId: String, destinationAccountId: String, amount: Double) {

        val sourceAccount: BankAccount = accounts.findById(sourceAccountId)
            ?: throw IllegalArgumentException("Account not found")
        val destinationAccount: BankAccount = accounts.findById(destinationAccountId)
            ?: throw IllegalArgumentException("Destination account not found")

        validateUserTransaction(sourceAccount, userId, amount)

        val transactionId = UUID.randomUUID().toString()
        val creationTime = Date()

        val transaction = Transaction(
            transactionId,
            creationTime,
            creationTime,
            sourceAccountId,
            destinationAccountId,
            userId,
            amount
        )

        transactions.save(transactionId, transaction)

        sourceAccount.deductAmount(amount)
        destinationAccount.addAmount(amount)

        accounts.save(sourceAccountId, sourceAccount)
        accounts.save(destinationAccountId, destinationAccount)

        transaction.completed()
        transactions.save(transactionId, transaction)
    }

    private fun validateUserTransaction(sourceAccount: BankAccount, userId: String, amount: Double) {
        if (userId != sourceAccount.userId) {
            throw IllegalArgumentException("User not authorized to perform transfer")
        }
        if (!sourceAccount.isAmountDeductible(amount)) {
            throw IllegalArgumentException("Not enough balance in the source account")
        }
    }
}