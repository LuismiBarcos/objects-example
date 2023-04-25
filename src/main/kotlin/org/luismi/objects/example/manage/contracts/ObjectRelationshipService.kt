package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
interface ObjectRelationshipService: BaseObjectService {

    /**
     * Create an object relationship
     * @param relationshipName
     * @param objectDefinitionId1
     * @param objectDefinitionId2
     * @param objectDefinitionName2
     * @param relationshipType
     * @return the id of the new object relationship
     */
    fun createObjectRelationship(
        relationshipName: String,
        objectDefinitionId1: String,
        objectDefinitionId2: String,
        objectDefinitionName2: String,
        relationshipType: String,
    ): Int

    /**
     * Deletes the objects relationships of an object
     * @param objectDefinitionId
     */
    fun deleteCustomObjectDefinitionRelationships(objectDefinitionId: Int)

}