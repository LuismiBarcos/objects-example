package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
enum class CustomObjects(
    private val objectName: String,
    private val pluralName: String,
    private val fieldName: String,
    private val customObjectResourceName: String,
    private val objectLayoutResourceName: String  = "/object-layout.txt"
) {

    UNIVERSITY("University", "Universities", "universityName", "/universities.txt"),
    STUDENT("Student", "Students", "studentName", "/students.txt", "/student-object-layout.txt"),
    SUBJECT("Subject", "Subjects", "subjectName", "/subjects.txt");

    val customObject: CustomObject
        get() {
            return CustomObject(objectName, pluralName, fieldName, customObjectResourceName, objectLayoutResourceName)
        }

}