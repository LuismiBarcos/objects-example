package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
interface ObjectLayoutService: BaseObjectService {
    /**
     * Create and object layout to an object definition
     * @param objectDefinitionId
     * @param customObject
     * @param relatedObjectDefinitionName
     * @param objectRelationshipId
     */
    fun createObjectLayout(
        objectDefinitionId: String,
        customObject: CustomObject,
        relatedObjectDefinitionName: String,
        objectRelationshipId: String
    )
}