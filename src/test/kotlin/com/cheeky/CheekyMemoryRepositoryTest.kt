package com.cheeky

import com.cheeky.core.BankAccount
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException

internal class CheekyMemoryRepositoryTest {

    private lateinit var sut: CheekyMemoryRepository<BankAccount>

    @BeforeEach
    internal fun setUp() {
        sut = Sut()
    }

    @Test
    fun save_whenIdDoesntExist_shouldAssignOneAndSave() {
        val account = BankAccount("DANJ8942JN", "EUR")
        val accountId = sut.save(account)

        assert(accountId != null)
        assert(account.id != null)

        val savedAccount = sut.findById(accountId)

        assertEquals(account.userId, savedAccount?.userId)
        assertEquals(account.id, savedAccount?.id)
    }


    @Test
    fun save_when2Updates_shouldFailWithVersionMismatch() {
        val account = BankAccount("DANJ8942JN", "EUR")
        val accountId = sut.save(account)

        val savedAccount = sut.findById(accountId)

        savedAccount!!.addAmount(120.00)

        val savedAccount2 = sut.findById(accountId)
        savedAccount2!!.addAmount(40.00)
        sut.save(savedAccount2)

        assertThrows<IllegalStateException> { sut.save(savedAccount) }
    }
}

class Sut: CheekyMemoryRepository<BankAccount>(CheekyLocks(false)) {
}