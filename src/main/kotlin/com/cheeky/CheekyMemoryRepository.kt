package com.cheeky

import com.cheeky.core.CheekyEntity
import java.util.UUID

abstract class CheekyMemoryRepository<T: CheekyEntity> {

    private val dataStore = hashMapOf<String, T>();

    fun save(entity: T) {
        if (entity.id == null) {
            entity.id = UUID.randomUUID().toString()
        }
        dataStore.put(entity.id, entity)
    }

    fun findById(id: String): T? {
        return dataStore.get(id)
    }
}