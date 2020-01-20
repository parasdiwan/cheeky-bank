package com.cheeky.core

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

abstract class CheekyEntity (
){
    internal var id: String = UUID.randomUUID().toString()
    internal var updateTime: Date = Date()
    internal var versionNumber: AtomicInteger = AtomicInteger(0)
}