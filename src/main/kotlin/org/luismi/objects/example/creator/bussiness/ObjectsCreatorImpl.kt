package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.contracts.ObjectDefinitionService
import org.luismi.objects.example.creator.contracts.ObjectLayoutService
import org.luismi.objects.example.creator.contracts.ObjectRelationshipService
import org.luismi.objects.example.creator.contracts.ObjectsCreator
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
@Component(classes = [ObjectsCreator::class])
class ObjectsCreatorImpl: ObjectsCreator {
    @Inject
    private lateinit var parser: Parser
    @Inject
    private lateinit var invoker: Invoker
    @Inject
    private lateinit var objectDefinitionService: ObjectDefinitionService
    @Inject
    private lateinit var objectRelationshipService: ObjectRelationshipService
    @Inject
    private lateinit var objectLayoutService: ObjectLayoutService

    override fun createObjects(askerOptions: AskerOptions) {
        val universityObjectDefinitionId = objectDefinitionService.createObjectDefinition("University", "universityName", "Universities")
        val subjectObjectDefinitionId = objectDefinitionService.createObjectDefinition("Subject", "subjectName", "Subjects")
        val studentObjectDefinitionId = objectDefinitionService.createObjectDefinition("Student", "studentName", "Students")

        val universityStudentsId = objectRelationshipService.createObjectRelationship(
            "universityStudents",
            universityObjectDefinitionId.toString(),
            studentObjectDefinitionId.toString(),
            "Student",
            "oneToMany"
        )

        val studentSubjectsId = objectRelationshipService.createObjectRelationship(
            "studentSubjects",
            studentObjectDefinitionId.toString(),
            subjectObjectDefinitionId.toString(),
            "Subject",
            "manyToMany"
        )

        objectLayoutService.createObjectLayout(
            universityObjectDefinitionId.toString(),
            "/object-layout.txt",
            "UniversityLayout",
            "University",
            "universityName",
            "Students",
            universityStudentsId.toString()
        )

        objectLayoutService.createObjectLayout(
            subjectObjectDefinitionId.toString(),
            "/object-layout.txt",
            "SubjectLayout",
            "Subject",
            "subjectName",
            "Students",
            (studentSubjectsId + 1).toString()
        )

        objectLayoutService.createObjectLayout(
            studentObjectDefinitionId.toString(),
            "/student-object-layout.txt",
            "StudentLayout",
            "Student",
            "studentName",
            "Subjects",
            (studentSubjectsId + 1).toString()
        )

        publishObjectDefinition(universityObjectDefinitionId.toString())
        publishObjectDefinition(subjectObjectDefinitionId.toString())
        publishObjectDefinition(studentObjectDefinitionId.toString())

        createCustomObjects("universities", "universityName", "/universities.txt", emptyList())
        createCustomObjects("Students", "studentName", "/students.txt", getUniversitiesIds("universities"))
        createCustomObjects("Subjects", "subjectName", "/subjects.txt", emptyList())
    }

    private fun publishObjectDefinition(objectDefinitionId: String) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.OBJECT_ADMIN_DEFINITION}/" +
                    "$objectDefinitionId${LiferayObjectsConstants.PUBLISH}",
            HTTPMethods.POST,
            ""
        )
    }

    private fun createCustomObjects(pluralName: String, fieldName: String, resourceName: String, additionalIds: List<Long>) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.C}/" +
                    "${pluralName.lowercase()}${LiferayObjectsConstants.BATCH}",
            HTTPMethods.POST,
            parser.parseText(
                getResource(resourceName),
                buildMap {
                    put(ParserConstants.FIELD_NAME, fieldName)
                    put(
                        ParserConstants.UNIVERSITY_1_ID,
                        if (additionalIds.isEmpty()) "" else additionalIds[0].toString()
                    )
                    put(
                        ParserConstants.UNIVERSITY_2_ID,
                        if (additionalIds.isEmpty()) "" else additionalIds[1].toString()
                    )
                }
            )
        )
    }

    private fun getUniversitiesIds(pluralName: String): List<Long> =
        JsonPath.read(
            invoker.invoke(
                "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.C}/" +
                        "${pluralName.lowercase()}/",
                HTTPMethods.GET,
                null
            ),
            "$.items[*].id"
        )

    private fun getResource(resourceName: String): String =
        ObjectsCreatorImpl::class.java.getResource(resourceName)?.readText() ?: ""
}