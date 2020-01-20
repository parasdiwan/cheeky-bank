package com.cheeky.core

import java.util.Date

class Transaction (
    internal val sourceAccountId: String,
    internal val destinationAccountId: String,
    internal val userId: String,
    internal val amount: Double
): CheekyEntity() {
}