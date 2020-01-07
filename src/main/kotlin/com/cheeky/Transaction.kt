package com.cheeky

import java.util.Date

class Transaction (
    val id: String,
    val creationTime: Date,
    var updateTime: Date,
    val sourceAccountId: String,
    val destinationAccountId: String,
    var status: String,
    val userId: String
) {

}