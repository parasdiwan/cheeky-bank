package com.cheeky

import com.cheeky.core.CheekyEntity
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class CheekyMemoryRepository<T: CheekyEntity> (
    private val locks : CheekyLocks
) {

    private val dataStore = ConcurrentHashMap<String, T>();

    fun save(entity: T): String {
        val key = entity.id
        locks.lock(key)

        validateVersioning(key, entity.versionNumber)
        val entityToSave = entity.copy(Date(), UUID.randomUUID().toString()) as T
        dataStore[key] = entityToSave

        locks.unlock(key)
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