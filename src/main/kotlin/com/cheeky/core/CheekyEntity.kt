package com.cheeky.core

import java.util.Date

abstract class CheekyEntity (
    internal val id: String,
    private val creationTime: Date,
    private var updateTime: Date
){
}