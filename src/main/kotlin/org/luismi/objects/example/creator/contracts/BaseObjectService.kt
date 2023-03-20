package org.luismi.objects.example.creator.contracts

/**
 * @author Luis Miguel Barcos
 */
interface BaseObjectService {

    fun getResource(resourceName: String): String =
        BaseObjectService::class.java.getResource(resourceName)?.readText() ?: ""
}