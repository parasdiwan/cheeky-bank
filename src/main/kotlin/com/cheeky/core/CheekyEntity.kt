package com.cheeky.core

import java.util.Date

open class CheekyEntity (
    internal val id: String,
    internal val creationTime: Date,
    internal val updateTime: Date
){
}