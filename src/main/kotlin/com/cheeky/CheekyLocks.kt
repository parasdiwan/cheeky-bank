package com.cheeky

import com.cheeky.di.CheekyUnit
import com.google.common.util.concurrent.Striped
import java.util.concurrent.locks.Lock

@CheekyUnit
class CheekyLocks {

    private var locks: Striped<Lock> = Striped.lock(1)

    fun lock(key: String) {
        locks.get(key).lock()
    }

    fun unlock(key: String) {
        locks.get(key).unlock()
    }
}
