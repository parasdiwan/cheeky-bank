package com.cheeky.core

import com.cheeky.CheekyMemoryRepository
import com.cheeky.di.CheekyUnit

@CheekyUnit
class TransactionRepository: CheekyMemoryRepository<String, Transaction>() {
}