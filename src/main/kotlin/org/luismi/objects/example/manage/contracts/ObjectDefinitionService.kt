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

    /**
     * Get all the object definitions in the current Liferay portal instance
     * @return a list with all the object definition ids
     */
    fun getAllCustomObjectDefinitions(): List<Int>
}