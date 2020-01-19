package com.cheeky.core

import com.cheeky.CheekyMemoryRepository
import com.cheeky.di.CheekyUnit

@CheekyUnit
class BankAccountRepository: CheekyMemoryRepository<String, BankAccount>() {
}