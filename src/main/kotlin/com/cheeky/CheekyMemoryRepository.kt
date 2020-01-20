package com.cheeky

import com.cheeky.core.CheekyEntity
import java.util.Date
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

abstract class CheekyMemoryRepository<T: CheekyEntity> {

    private val dataStore = ConcurrentHashMap<String, T>();

    fun save(entity: T): String {
        val key = entity.id
        if (key == null) {
            entity.id = UUID.randomUUID().toString()
        }
        validateVersioning(key, entity.versionNumber)
        entity.updateTime = Date()
        dataStore[key] = entity
        return entity.id
    }

    private fun validateVersioning(key: String, versionNumber: String) {
        if (dataStore.containsKey(key)
            && dataStore.get(key)!!.versionNumber != versionNumber
        ) {
            throw RuntimeException("Entity update failed: Version mismatch")
        }
    }

    fun findById(id: String): T? {
        return dataStore.get(id)
    }
}