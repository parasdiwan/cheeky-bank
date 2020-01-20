package com.cheeky

import com.google.common.util.concurrent.Striped
import java.util.concurrent.locks.Lock

class CheekyLocks(
    private val use: Boolean
) {

    private var locks: Striped<Lock> = Striped.lock(1)

    fun lock(key: String) {
        if (!use) {
            return
        }
        locks.get(key).lock()
    }

    @Synchronized
    fun unlock(key: String) {
        locks.get(key).unlock()
    }
}
