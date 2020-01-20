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
        val updatedVersion = UUID.randomUUID().toString()
        val entityToSave = entity.copy(Date(), updatedVersion) as T
        dataStore[key] = entityToSave

        return entity.id
    }

    @Synchronized
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