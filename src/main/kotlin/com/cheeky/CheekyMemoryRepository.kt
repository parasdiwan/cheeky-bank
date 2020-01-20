package com.cheeky

abstract class CheekyMemoryRepository<ID, T> {

    protected val dataStore = hashMapOf<ID, T>();

    fun save(id: ID, entity: T?) {
        if (entity == null) {
            return
        }
        dataStore.put(id, entity)
    }

    fun findById(id: ID): T? {
        return dataStore.get(id)
    }
}