package org.luismi.objects.example.creator.bussiness

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
        createUniversityObjectDefinition()
        createSubjectObjectDefinition()
        createStudentObjectDefinition()
    }

    private fun createUniversityObjectDefinition() {
        invoker.invoke(
            createObjectDefinitionPOST(),
            HTTPMethods.POST,
            parseObjectDefinition("University", "universityName", "universities")
        )
    }

    private fun createSubjectObjectDefinition() {
        invoker.invoke(
            createObjectDefinitionPOST(),
            HTTPMethods.POST,
            parseObjectDefinition("Subject", "subjectName", "Subjects")
        )
    }

    private fun createStudentObjectDefinition() {
        invoker.invoke(
            createObjectDefinitionPOST(),
            HTTPMethods.POST,
            parseObjectDefinition("Student", "studentName", "Students")
        )
    }

    private fun createObjectDefinitionPOST(): String =
        "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}"

    private fun parseObjectDefinition(name: String, fieldName: String, pluralName: String): String {
        return parser.parseText(
            getResource("/object-definition.txt"),
            buildMap {
                put(ParserConstants.NAME, name)
                put(ParserConstants.FIELD_NAME, fieldName)
                put(ParserConstants.PLURAL_NAME, pluralName)
            }
        )
    }

    private fun getResource(resourceName: String): String =
        ObjectsCreatorImpl::class.java.getResource(resourceName)?.readText() ?: ""
}