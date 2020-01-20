package com.cheeky

import com.cheeky.core.CheekyEntity
import java.util.Date
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

abstract class CheekyMemoryRepository<T: CheekyEntity> {

    private val dataStore = ConcurrentHashMap<String, T>();

    fun save(entity: T) {
        if (entity.id == null) {
            entity.id = UUID.randomUUID().toString()
        }
        entity.updateTime = Date()
        dataStore[entity.id] = entity
    }

    fun findById(id: String): T? {
        return dataStore.get(id)
    }
}