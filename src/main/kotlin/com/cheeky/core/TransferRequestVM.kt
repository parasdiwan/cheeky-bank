package com.cheeky.core

data class TransferRequestVM (
    val userId: String,
    val sourceAccountId: String,
    val destAccountId: String,
    val amount: Double
){
}