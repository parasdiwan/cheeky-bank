package com.cheeky

import com.cheeky.core.CheekyEntity
import java.util.concurrent.ConcurrentHashMap

abstract class CheekyMemoryRepository<T: CheekyEntity> () {

    private val dataStore = ConcurrentHashMap<String, T>()

    fun findById(id: String): T? {
        return dataStore[id]
    }

    fun save(entity: T) {
        dataStore.put(entity.id, entity)
    }

    fun snapshot(key: String) {

    }
}