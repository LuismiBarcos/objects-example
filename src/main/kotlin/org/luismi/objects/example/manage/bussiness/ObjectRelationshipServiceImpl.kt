package org.luismi.objects.example.manage.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.manage.contracts.ObjectRelationshipService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectRelationshipService::class])
class ObjectRelationshipServiceImpl: ObjectRelationshipService, BaseObjectServiceImpl() {

    private val objectRelationshipResourceName = "/object-relationship.txt"

    override fun createObjectRelationship(
        relationshipName: String,
        objectDefinitionId1: String,
        objectDefinitionId2: String,
        objectDefinitionName2: String,
        relationshipType: String,
    ): Int =
        JsonPath
            .read(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                            "$objectDefinitionId1${LiferayObjectsConstants.OBJECT_RELATIONSHIPS}",
                    HTTPMethods.POST,
                    parser.parseText(
                        getResource(objectRelationshipResourceName),
                        buildMap {
                            put(ParserConstants.RELATIONSHIP_NAME, relationshipName)
                            put(ParserConstants.OBJECT_DEFINITION_ID_1, objectDefinitionId1)
                            put(ParserConstants.OBJECT_DEFINITION_ID_2, objectDefinitionId2)
                            put(ParserConstants.OBJECT_DEFINITION_NAME_2, objectDefinitionName2)
                            put(ParserConstants.RELATIONSHIP_TYPE, relationshipType)
                        }
                    )
                ), "id")

    override fun deleteCustomObjectDefinitionRelationships(objectDefinitionId: Int) {
        getCustomObjectDefinitionRelationships(objectDefinitionId).forEach {
            invoker.invoke("${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN}" +
                    "${LiferayObjectsConstants.OBJECT_RELATIONSHIPS}/$it",
                HTTPMethods.DELETE,
                null)
        }
    }

    private fun getCustomObjectDefinitionRelationships(objectDefinitionId: Int): List<Int> =
        JsonPath
            .parse(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                            "$objectDefinitionId${LiferayObjectsConstants.OBJECT_RELATIONSHIPS}?restrictFields=actions&fields=id,reverse",
                    HTTPMethods.GET,
                    null
                ))
            .read("\$..[?(@.reverse == false)].id")

}