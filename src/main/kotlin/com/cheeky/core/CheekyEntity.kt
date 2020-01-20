package com.cheeky.core

import java.util.*

abstract class CheekyEntity (
){
    internal lateinit var id: String
    internal var updateTime: Date = Date()
    internal var versionNumber: String = UUID.randomUUID().toString()
}