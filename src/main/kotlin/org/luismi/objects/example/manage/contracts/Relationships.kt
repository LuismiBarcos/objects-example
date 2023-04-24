package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */

enum class RelationshipTypes(val value: String) {
    ONE_TO_MANY("oneToMany"),
    MANY_TO_MANY("manyToMany");
}

enum class Relationships(val relationshipName: String) {
    UNIVERSITY_STUDENTS("universityStudents"),
    STUDENT_SUBJECTS("studentSubjects")
}