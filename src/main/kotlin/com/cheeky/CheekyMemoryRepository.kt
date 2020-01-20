package com.cheeky

import com.cheeky.core.CheekyEntity
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class CheekyMemoryRepository<T: CheekyEntity> {

    private val dataStore = ConcurrentHashMap<String, T>();

    fun save(entity: T): String {
        val key = entity.id
        validateVersioning(key, entity.versionNumber)
        entity.updateTime = Date()
        entity.versionNumber = UUID.randomUUID().toString()
        dataStore[key] = entity
        return entity.id
    }

    private fun validateVersioning(key: String, entityVersionNumber: String) {
        if (dataStore.containsKey(key)
            && dataStore[key]!!.versionNumber != entityVersionNumber
        ) {
            throw IllegalStateException("Entity update failed: Version mismatch")
        }
    }

    fun findById(id: String): T? {
        return dataStore[id]?.copy() as T?
    }
}