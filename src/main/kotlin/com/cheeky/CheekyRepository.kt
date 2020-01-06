package com.cheeky

interface CheekyRepository<ID, T> {
    fun save(id: ID, entity: T)
    fun findById(id: ID): T?
}