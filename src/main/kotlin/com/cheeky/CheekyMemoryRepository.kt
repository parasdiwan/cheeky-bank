package com.cheeky

open class CheekyMemoryRepository<ID, T>: CheekyRepository<ID, T?> {

    protected val dataStore = hashMapOf<ID, T>();

    override fun save(id: ID, entity: T?) {
        if (entity == null) {
            return
        }
        dataStore.put(id, entity)
    }

    override fun findById(id: ID): T? {
        return dataStore.get(id)
    }
}