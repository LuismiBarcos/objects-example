package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.creator.contracts.ObjectDefinitionService
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.Invoker
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.Parser
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component
import org.sdi.annotations.Inject

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectDefinitionService::class])
class ObjectDefinitionServiceImpl: ObjectDefinitionService {
    @Inject
    private lateinit var invoker: Invoker

    @Inject
    private lateinit var parser: Parser

    override fun publishObjectDefinition(objectDefinitionId: String) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.PUBLISH}",
            HTTPMethods.POST,
            ""
        )
    }

    override fun createObjectDefinition(name: String, fieldName: String, pluralName: String): Int =
        JsonPath
            .read(
                invoker.invoke(
                    "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}",
                    HTTPMethods.POST,
                    parser.parseText(
                        getResource("/object-definition.txt"),
                        buildMap {
                            put(ParserConstants.NAME, name)
                            put(ParserConstants.FIELD_NAME, fieldName)
                            put(ParserConstants.PLURAL_NAME, pluralName)
                        }
                    )
                ), "id")
}