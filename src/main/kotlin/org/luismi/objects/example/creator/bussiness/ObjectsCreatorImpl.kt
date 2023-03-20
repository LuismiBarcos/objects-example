package org.luismi.objects.example.creator.bussiness

import com.jayway.jsonpath.JsonPath
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.contracts.*
import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.LiferayObjectsConstants
import org.luismi.objects.example.template.parser.contracts.ParserConstants
import org.sdi.annotations.Component
import org.sdi.annotations.Inject

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectsCreator::class])
class ObjectsCreatorImpl: ObjectsCreator, BaseObjectServiceImpl() {
    @Inject
    private lateinit var objectDefinitionService: ObjectDefinitionService
    @Inject
    private lateinit var objectRelationshipService: ObjectRelationshipService
    @Inject
    private lateinit var objectLayoutService: ObjectLayoutService

    override fun createObjects(askerOptions: AskerOptions) {

        val university = CustomObjects.UNIVERSITY.customObject
        val student = CustomObjects.STUDENT.customObject
        val subject = CustomObjects.SUBJECT.customObject

        val universityObjectDefinitionId = objectDefinitionService.createObjectDefinition(university)
        val subjectObjectDefinitionId = objectDefinitionService.createObjectDefinition(subject)
        val studentObjectDefinitionId = objectDefinitionService.createObjectDefinition(student)

        val universityStudentsId = objectRelationshipService.createObjectRelationship(
            Relationships.UNIVERSITY_STUDENTS.relationshipName,
            universityObjectDefinitionId.toString(),
            studentObjectDefinitionId.toString(),
            student.name,
            RelationshipTypes.ONE_TO_MANY.value
        )

        val studentSubjectsId = objectRelationshipService.createObjectRelationship(
            Relationships.STUDENT_SUBJECTS.relationshipName,
            studentObjectDefinitionId.toString(),
            subjectObjectDefinitionId.toString(),
            subject.name,
            RelationshipTypes.MANY_TO_MANY.value
        )

        objectLayoutService.createObjectLayout(
            universityObjectDefinitionId.toString(),
            university,
            student.pluralName,
            universityStudentsId.toString()
        )

        objectLayoutService.createObjectLayout(
            subjectObjectDefinitionId.toString(),
            subject,
            student.pluralName,
            (studentSubjectsId + 1).toString()
        )

        objectLayoutService.createObjectLayout(
            studentObjectDefinitionId.toString(),
            student,
            subject.pluralName,
            (studentSubjectsId + 1).toString()
        )

        objectDefinitionService.publishObjectDefinition(universityObjectDefinitionId.toString())
        objectDefinitionService.publishObjectDefinition(subjectObjectDefinitionId.toString())
        objectDefinitionService.publishObjectDefinition(studentObjectDefinitionId.toString())

        createCustomObjects(university, emptyList())
        createCustomObjects(student, getUniversitiesIds(university.pluralName))
        createCustomObjects(subject, emptyList())
    }

    private fun createCustomObjects(customObject: CustomObject, additionalIds: List<Long>) {
        invoker.invoke(
            "${LiferayObjectsConstants.SERVER}${LiferayObjectsConstants.C}/" +
                    "${customObject.pluralName.lowercase()}${LiferayObjectsConstants.BATCH}",
            HTTPMethods.POST,
            parser.parseText(
                getResource(customObject.customObjectResourceName),
                buildMap {
                    put(ParserConstants.FIELD_NAME, customObject.fieldName)
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
}