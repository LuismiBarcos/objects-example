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

        val universityStudentsId = createObjectRelationship(
            "universityStudents",
            universityObjectDefinitionId.toString(),
            studentObjectDefinitionId.toString(),
            "Student",
            "oneToMany"
        )

        val studentSubjectsId = createObjectRelationship(
            "studentSubjects",
            studentObjectDefinitionId.toString(),
            subjectObjectDefinitionId.toString(),
            "Subject",
            "manyToMany"
        )

        createObjectLayout(
            universityObjectDefinitionId.toString(),
            "/object-layout.txt",
            "UniversityLayout",
            "University",
            "universityName",
            "Students",
            universityStudentsId.toString()
        )

        createObjectLayout(
            subjectObjectDefinitionId.toString(),
            "/object-layout.txt",
            "SubjectLayout",
            "Subject",
            "subjectName",
            "Students",
            (studentSubjectsId + 1).toString()
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

    private fun createObjectRelationship(
        relationshipName: String,
        objectDefinitionId1: String,
        objectDefinitionId2: String,
        objectDefinitionName2: String,
        relationshipType: String
    ): Int =
        JsonPath
            .parse(
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
                )
            )
            .read("id")

    private fun createObjectLayout(
        objectDefinitionId: String,
        resourceName: String,
        name: String,
        objectDefinitionName: String,
        fieldName: String,
        relatedObjectDefinitionName: String,
        objectRelationshipId: String
    ) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.OBJECT_LAYOUTS}",
            HTTPMethods.POST,
            parser.parseText(
                getResource(resourceName),
                buildMap {
                    put(ParserConstants.NAME, name)
                    put(ParserConstants.OBJECT_DEFINITION_ID, objectDefinitionId)
                    put(ParserConstants.OBJECT_DEFINITION_NAME, objectDefinitionName)
                    put(ParserConstants.FIELD_NAME, fieldName)
                    put(ParserConstants.RELATED_OBJECT_DEFINITION_NAME, relatedObjectDefinitionName)
                    put(ParserConstants.OBJECT_RELATIONSHIP_ID, objectRelationshipId)
                }
            )
        )
    }

    private fun getResource(resourceName: String): String =
        ObjectsCreatorImpl::class.java.getResource(resourceName)?.readText() ?: ""
}