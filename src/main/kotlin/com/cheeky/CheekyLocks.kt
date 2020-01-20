package com.cheeky

import com.google.common.util.concurrent.Striped
import java.util.concurrent.locks.Lock

class CheekyLocks {

    private var locks: Striped<Lock> = Striped.lock(1)

    fun lock(key: String) {
        locks.get(key).lock()
    }

    fun unlock(key: String) {
        locks.get(key).unlock()
    }
}
