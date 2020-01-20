package com.cheeky.core

import java.util.Date

abstract class CheekyEntity (
){
    internal lateinit var id: String
    internal var updateTime: Date = Date()
}