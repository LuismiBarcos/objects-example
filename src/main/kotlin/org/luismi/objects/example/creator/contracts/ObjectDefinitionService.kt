package org.luismi.objects.example.creator.contracts

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
     * @param fieldName
     * @param name
     * @param pluralName
     * @return the created object definition Id
     */
    fun createObjectDefinition(name: String, fieldName: String, pluralName: String): Int
}