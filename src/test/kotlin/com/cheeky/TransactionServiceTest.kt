package com.cheeky

import com.cheeky.core.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.lang.IllegalArgumentException
import java.util.*

internal class TransactionServiceTest {

    companion object {
        const val SOURCE_ACCOUNT_ID = "1FSD5HJ2L"
        const val DEST_ACCOUNT_ID = "508FAS32L"
        const val USER_ID = "3429023"
        const val BALANCE = 120.0
    }

    private var transactions: TransactionRepository = mock(TransactionRepository::class.java)
    private var accounts: BankAccountRepository = mock(BankAccountRepository::class.java)

    private val transactionCaptor = ArgumentCaptor.forClass<Transaction, Transaction>(Transaction::class.java)
    private val bankAccountCaptor = ArgumentCaptor.forClass<BankAccount, BankAccount>(BankAccount::class.java)

    private val sut = TransactionService(transactions, accounts)

    @BeforeEach
    internal fun setUp() {
        `when`(accounts.findById(SOURCE_ACCOUNT_ID)).thenReturn(newBankAccountId(SOURCE_ACCOUNT_ID))
        `when`(accounts.findById(DEST_ACCOUNT_ID)).thenReturn(newBankAccountId(DEST_ACCOUNT_ID))
    }

    @Test
    internal fun transferMoney_whenNotEnoughBalance_shouldThrowException() {
        assertThrows<IllegalArgumentException> {
            sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, 355.00)
        }
    }

    @Test
    internal fun transferMoney_whenEnoughBalance_shouldCompleteTransfer() {
        val amount = 21.00
        sut.transferMoney(USER_ID, SOURCE_ACCOUNT_ID, DEST_ACCOUNT_ID, amount)

        verify(transactions, times(2)).save(any(), transactionCaptor.capture())
        verify(accounts, times(2)).save(any(), bankAccountCaptor.capture())

        assert(transactionCaptor.value.amount.equals(amount))
        assert(transactionCaptor.value.amount.equals(amount))
        val sourceBalance = bankAccountCaptor.value.getBalance()
        assert(sourceBalance.equals(sourceBalance - amount))
        val destBalance = bankAccountCaptor.value.getBalance()
        assert(destBalance.equals(destBalance + amount))
    }

    private fun newBankAccountId(accountId: String): BankAccount {
        return BankAccount(
            accountId,
            Date(),
            Date(),
            USER_ID,
            BALANCE,
            "EUR"
        )
    }
}