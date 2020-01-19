package com.cheeky

import com.cheeky.core.BankAccount
import com.cheeky.core.BankAccountRepository
import com.cheeky.core.TransactionRepository
import com.cheeky.core.TransactionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
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