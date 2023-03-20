package org.luismi.objects.example.creator.contracts

/**
 * @author Luis Miguel Barcos
 */
interface ObjectLayoutService: BaseObjectService {
    /**
     * Create and object layout to an object definition
     * @param objectDefinitionId
     * @param resourceName
     * @param name
     * @param objectDefinitionName
     * @param fieldName
     * @param relatedObjectDefinitionName
     * @param objectRelationshipId
     */
    fun createObjectLayout(
        objectDefinitionId: String,
        resourceName: String,
        name: String,
        objectDefinitionName: String,
        fieldName: String,
        relatedObjectDefinitionName: String,
        objectRelationshipId: String
    )
}