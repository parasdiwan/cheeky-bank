package com.cheeky.core

import com.cheeky.CheekyLocks
import com.cheeky.CheekyMemoryRepository
import com.cheeky.di.CheekyUnit

@CheekyUnit
open class TransactionRepository: CheekyMemoryRepository<Transaction>(CheekyLocks()) {
}