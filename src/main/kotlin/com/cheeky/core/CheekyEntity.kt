package com.cheeky.core

import java.util.*

abstract class CheekyEntity (
){
    internal var id: String = UUID.randomUUID().toString()
    internal var updateTime: Date = Date()
    internal var versionNumber: String = UUID.randomUUID().toString()

    abstract fun copy(updateTime: Date = this.updateTime, versionNumber: String = this.versionNumber): CheekyEntity
}