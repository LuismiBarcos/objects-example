package org.luismi.objects.example.dependency.injector

import kotlin.reflect.KClass

/**
 * @author Luis Miguel Barcos
 */
object DependencyInjector {
    private val dependencies: HashMap<KClass<*>, Any> = HashMap()

    fun addDependency(interfaze: KClass<*>, implementation: Any) = dependencies.put(interfaze, implementation)

    fun <T> getDependency(interfaze: KClass<*>): T = dependencies[interfaze]!! as T
}