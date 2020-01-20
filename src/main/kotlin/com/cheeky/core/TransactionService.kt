package com.cheeky.core

import com.cheeky.CheekyLocks
import com.cheeky.di.CheekyUnit
import com.cheeky.di.Inject
import java.lang.IllegalArgumentException
import java.util.*

@CheekyUnit
class TransactionService public @Inject constructor(
    private var transactions: TransactionRepository,
    private var accounts: BankAccountRepository,
    private val locks : CheekyLocks
) {


    fun transferMoney(userId: String, sourceAccountId: String, destinationAccountId: String, amount: Double) {

        val sourceAccount: BankAccount = accounts.findById(sourceAccountId)
            ?: throw IllegalArgumentException("Account not found")
        val destinationAccount: BankAccount = accounts.findById(destinationAccountId)
            ?: throw IllegalArgumentException("Destination account not found")

        validateUserTransaction(sourceAccount, userId, amount)

        val transaction = Transaction(
            sourceAccountId,
            destinationAccountId,
            userId,
            amount
        )

        val transactionKey: String = prepareKey(sourceAccountId, destinationAccountId)
        locks.lock(transactionKey)
        sourceAccount.deductAmount(amount)
        destinationAccount.addAmount(amount)

        accounts.save(sourceAccount)
        accounts.save(destinationAccount)

        transaction.completed()
        locks.unlock(transactionKey)
        transactions.save(transaction)
    }

    private fun prepareKey(sourceAccountId: String, destinationAccountId: String): String {
        return Objects.hash(sourceAccountId, destinationAccountId).toString()
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