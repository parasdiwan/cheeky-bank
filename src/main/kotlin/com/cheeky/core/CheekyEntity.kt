package com.cheeky.core

import java.util.Date

abstract class CheekyEntity (
    internal val id: String,
    internal val creationTime: Date,
    internal val updateTime: Date
){
}