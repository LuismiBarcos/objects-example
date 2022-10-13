package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.contracts.ObjectsCreator
import org.luismi.objects.example.dependency.injector.DependencyInjector
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.Invoker
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.Parser
import org.luismi.objects.example.template.parser.contracts.ParserConstants

/**
 * @author Luis Miguel Barcos
 */
class ObjectsCreatorImpl: ObjectsCreator {
    private val parser = DependencyInjector.getDependency<Parser>(Parser::class)
    private val invoker = DependencyInjector.getDependency<Invoker>(Invoker::class)

    override fun createObjects(askerOptions: AskerOptions) {
        val universityObjectDefinitionId = createObjectDefinition("University", "universityName", "universities")
        val subjectObjectDefinitionId = createObjectDefinition("Subject", "subjectName", "Subjects")
        val studentObjectDefinitionId = createObjectDefinition("Student", "studentName", "Students")

        println("Object definitions:" +
                "\n\tUniversity: $universityObjectDefinitionId" +
                "\n\tSubject: $subjectObjectDefinitionId" +
                "\n\tStudent: $studentObjectDefinitionId"
        )
    }

    private fun createObjectDefinition(name: String, fieldName: String, pluralName: String): Int =
        JsonPath
            .parse(
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
                )
            )
            .read("id")

    private fun getResource(resourceName: String): String =
        ObjectsCreatorImpl::class.java.getResource(resourceName)?.readText() ?: ""
}