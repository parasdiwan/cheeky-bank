package com.cheeky.core

import com.cheeky.CheekyMemoryRepository
import com.cheeky.di.CheekyUnit

@CheekyUnit
class TransactionRepository: CheekyMemoryRepository<String, Transaction>() {

    companion object {
        lateinit var INSTANCE: TransactionRepository

        fun getInstance(): TransactionRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    TransactionRepository()
            }
            return INSTANCE
        }
    }
}