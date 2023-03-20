package org.luismi.objects.example.creator.bussiness

import org.luismi.objects.example.creator.contracts.CustomObject
import org.luismi.objects.example.creator.contracts.ObjectLayoutService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectLayoutService::class])
class ObjectLayoutServiceImpl: ObjectLayoutService, BaseObjectServiceImpl() {

    override fun createObjectLayout(
        objectDefinitionId: String,
        customObject: CustomObject,
        relatedObjectDefinitionName: String,
        objectRelationshipId: String,
    ) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.OBJECT_LAYOUTS}",
            HTTPMethods.POST,
            parser.parseText(
                getResource(customObject.objectLayoutResourceName),
                buildMap {
                    put(ParserConstants.NAME, customObject.layoutName)
                    put(ParserConstants.OBJECT_DEFINITION_ID, objectDefinitionId)
                    put(ParserConstants.OBJECT_DEFINITION_NAME, customObject.name)
                    put(ParserConstants.FIELD_NAME, customObject.fieldName)
                    put(ParserConstants.RELATED_OBJECT_DEFINITION_NAME, relatedObjectDefinitionName)
                    put(ParserConstants.OBJECT_RELATIONSHIP_ID, objectRelationshipId)
                }
            )
        )
    }
}