package com.cheeky.di

import org.reflections.Reflections
import org.reflections.scanners.TypeAnnotationsScanner

class DependencyManager (
    private val scanPackage: String
) {

    private val dependenciesByClass = mutableMapOf<Class<*>, Any>()

    fun initialize() {
        val reflections = Reflections(scanPackage, TypeAnnotationsScanner())
        val types = reflections.getTypesAnnotatedWith(CheekyUnit::class.java, true)
        for (type in types) {
            dependenciesByClass[type] = createDependency(type)
        }
        println(dependenciesByClass)
    }

    private fun createDependency(type: Class<*>): Any {
        try {
            return type.getConstructor().newInstance()
        } catch (e: NoSuchMethodException) {
            val parameterTypes = type.getDeclaredConstructor().parameterTypes
            val initializedParams = mutableListOf<Any>()
            for (paramType in parameterTypes) {
                val parameter = createDependency(paramType)
                initializedParams.add(parameter)
            }
            return type.getDeclaredConstructor().newInstance(initializedParams)
        }
    }
}