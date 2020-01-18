package com.cheeky.core

import com.cheeky.CheekyMemoryRepository

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