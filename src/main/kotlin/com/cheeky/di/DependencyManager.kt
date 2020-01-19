package com.cheeky.di

import org.reflections.Reflections
import org.reflections.scanners.TypeAnnotationsScanner
import java.lang.reflect.Constructor
import java.util.*

class DependencyManager(
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
        if (dependenciesByClass.containsKey(type)) return dependenciesByClass[type]!!
        val constructor = resolveConstructor(type)
        return if (constructor.parameterCount == 0) {
            constructor.newInstance()
        } else {
            val parameterTypes = constructor.parameterTypes
            val initializedParams = arrayOfNulls<Any>(constructor.parameterCount)
            for (index in 0 until constructor.parameterCount) {
                val paramType = parameterTypes[index]
                val parameter = createDependency(paramType)
                initializedParams[index] = parameter
            }
            constructor.newInstance(*initializedParams)
        }
    }

    private fun <T> resolveConstructor(type: Class<T>): Constructor<T> {
        return Arrays.stream(type.declaredConstructors)
            .filter { it.isAnnotationPresent(Inject::class.java) }
            .findFirst()
            .orElseGet { type.getConstructor() } as Constructor<T>
    }
}