package org.luismi.objects.example.creator.bussiness

import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.contracts.ObjectsCreator
import org.luismi.objects.example.dependency.injector.DependencyInjector
import org.luismi.objects.example.template.parser.contracts.Parser
import org.luismi.objects.example.template.parser.contracts.ParserConstants

/**
 * @author Luis Miguel Barcos
 */
class ObjectsCreatorImpl: ObjectsCreator {
    private val parser = DependencyInjector.getDependency<Parser>(Parser::class)

    override fun createObjects(askerOptions: AskerOptions) {
        createUniversityObjectDefinition()
        createSubjectObjectDefinition()
        createStudentObjectDefinition()
    }

    private fun createUniversityObjectDefinition() {
        println(parseObjectDefinition("University", "universityName", "universities"))
    }

    private fun createSubjectObjectDefinition() {
        println(parseObjectDefinition("Subject", "subjectName", "Subjects"))
    }

    private fun createStudentObjectDefinition() {
        println(parseObjectDefinition("Student", "studentName", "Students"))
    }

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