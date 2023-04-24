package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
interface ObjectRelationshipService: BaseObjectService {

    fun createObjectRelationship(
        relationshipName: String,
        objectDefinitionId1: String,
        objectDefinitionId2: String,
        objectDefinitionName2: String,
        relationshipType: String,
    ): Int

}