package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.creator.contracts.CustomObject
import org.luismi.objects.example.creator.contracts.ObjectDefinitionService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectDefinitionService::class])
class ObjectDefinitionServiceImpl: ObjectDefinitionService, BaseObjectServiceImpl() {

    override fun publishObjectDefinition(objectDefinitionId: String) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.PUBLISH}",
            HTTPMethods.POST,
            ""
        )
    }

    override fun createObjectDefinition(customObject: CustomObject): Int =
        JsonPath
            .read(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}",
                    HTTPMethods.POST,
                    parser.parseText(
                        getResource("/object-definition.txt"),
                        buildMap {
                            put(ParserConstants.NAME, customObject.name)
                            put(ParserConstants.FIELD_NAME, customObject.fieldName)
                            put(ParserConstants.PLURAL_NAME, customObject.pluralName)
                        }
                    )
                ), "id")
}