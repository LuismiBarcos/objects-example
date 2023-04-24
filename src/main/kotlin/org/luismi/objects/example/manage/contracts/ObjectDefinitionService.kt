package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
interface ObjectDefinitionService: BaseObjectService {

    /**
     * Publish a object definition by its Id
     * @param objectDefinitionId
     */
    fun publishObjectDefinition(objectDefinitionId: String)

    /**
     * Create an object definition
     * @param customObject
     * @return the created object definition Id
     */
    fun createObjectDefinition(customObject: CustomObject): Int
}