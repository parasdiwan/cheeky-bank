package com.cheeky

import com.cheeky.core.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TransactionServiceTest {

    companion object {
        const val SOURCE_ACCOUNT_ID = "1FSD5HJ2L"
        const val DEST_ACCOUNT_ID = "508FAS32L"
        const val USER_ID = "3429023"
        const val BALANCE = 120.0
    }

    private var transactions = mockk<TransactionRepository>(relaxed = true)
    private var accounts = mockk<BankAccountRepository>(relaxed = true)

    private val sut = TransactionService(transactions, accounts)

    @BeforeEach
    internal fun setUp() {
        every { accounts.findById(SOURCE_ACCOUNT_ID) } returns newBankAccountId(SOURCE_ACCOUNT_ID)
        every { accounts.findById(DEST_ACCOUNT_ID) } returns newBankAccountId(DEST_ACCOUNT_ID)
    }

    @Test
    internal fun transferMoney_whenNotEnoughBalance_shouldThrowException() {
        assertThrows<IllegalArgumentException> {
            sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, 355.00)
        }

        verify(exactly = 0) { transactions.save(any()) }
    }

    @Test
    internal fun transferMoney_whenEnoughBalance_shouldCompleteTransfer() {
        val sourceAccount = newBankAccountId(SOURCE_ACCOUNT_ID)
        every { accounts.findById(SOURCE_ACCOUNT_ID) } returns sourceAccount
        val destAccount = newBankAccountId(DEST_ACCOUNT_ID)
        every { accounts.findById(DEST_ACCOUNT_ID) } returns destAccount

        val amount = 21.00
        sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, amount)

        val savedTransactions = slot<Transaction>()
        verify(exactly = 1) {
            transactions.save(capture(savedTransactions))
        }

        assertEquals(amount, savedTransactions.captured.amount)

        assertEquals(BALANCE - amount, sourceAccount.getBalance())
        assertEquals(BALANCE + amount, destAccount.getBalance())
    }

    @Test
    fun transferMoney_whenAccountsDontExist_shouldThrowException() {
        every { accounts.findById(SOURCE_ACCOUNT_ID) } returns null
        assertThrows<IllegalArgumentException> {
            sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, 23.00)
        }

        every { accounts.findById(DEST_ACCOUNT_ID) } returns null
        assertThrows<IllegalArgumentException> {
            sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, 40.00)
        }

    }

    // TODO add test with loads of threads using the service

    private fun newBankAccountId(accountId: String): BankAccount {
        val bankAccount = BankAccount(
            USER_ID,
            "EUR"
        )
        bankAccount.id = accountId
        bankAccount.addAmount(BALANCE)

        return bankAccount
    }
}