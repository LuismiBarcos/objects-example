package org.luismi.objects.example.manage.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.manage.contracts.CustomObject
import org.luismi.objects.example.manage.contracts.ObjectDefinitionService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectDefinitionService::class])
class ObjectDefinitionServiceImpl: ObjectDefinitionService, BaseObjectServiceImpl() {

    private val objectDefinitionResourceName = "/object-definition.txt"

    override fun publishObjectDefinition(objectDefinitionId: String) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.PUBLISH}",
            HTTPMethods.POST,
            ""
        )
    }

    override fun createObjectDefinition(customObject: CustomObject): Int {
        return JsonPath
            .read(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}",
                    HTTPMethods.POST,
                    parser.parseText(
                        getResource(objectDefinitionResourceName),
                        buildMap {
                            put(ParserConstants.NAME, customObject.name)
                            put(ParserConstants.FIELD_NAME, customObject.fieldName)
                            put(ParserConstants.PLURAL_NAME, customObject.pluralName)
                        }
                    )
                ), "id")
    }

    override fun getAllCustomObjectDefinitions(): List<Int> =
        JsonPath
            .parse(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}" +
                            "?restrictFields=actions&fields=id,system",
                    HTTPMethods.GET,
                    null
                ))
            .read("\$..[?(@.system == false)].id")
}