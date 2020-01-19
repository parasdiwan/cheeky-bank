package com.cheeky.core

import com.cheeky.CheekyMemoryRepository
import com.cheeky.di.CheekyUnit

@CheekyUnit
class BankAccountRepository: CheekyMemoryRepository<String, BankAccount>() {

    companion object {
        lateinit var INSTANCE: BankAccountRepository

        fun getInstance(): BankAccountRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    BankAccountRepository()
            }
            return INSTANCE
        }
    }
}