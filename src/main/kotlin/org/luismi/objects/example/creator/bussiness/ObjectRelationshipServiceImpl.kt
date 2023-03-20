package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.creator.contracts.ObjectRelationshipService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectRelationshipService::class])
class ObjectRelationshipServiceImpl: ObjectRelationshipService, BaseObjectServiceImpl() {

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
                        getResource("/object-relationship.txt"),
                        buildMap {
                            put(ParserConstants.RELATIONSHIP_NAME, relationshipName)
                            put(ParserConstants.OBJECT_DEFINITION_ID_1, objectDefinitionId1)
                            put(ParserConstants.OBJECT_DEFINITION_ID_2, objectDefinitionId2)
                            put(ParserConstants.OBJECT_DEFINITION_NAME_2, objectDefinitionName2)
                            put(ParserConstants.RELATIONSHIP_TYPE, relationshipType)
                        }
                    )
                ), "id")

}