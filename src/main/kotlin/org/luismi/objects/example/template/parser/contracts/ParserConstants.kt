package org.luismi.objects.example.template.parser.contracts

/**
 * @author Luis Miguel Barcos
 */
enum class ParserConstants(val text: String) {
    NAME("#{name}"),
    PLURAL_NAME("#{pluralName}"),
    FIELD_NAME("#{fieldName}"),
    RELATIONSHIP_NAME("#{relationshipName}"),
    OBJECT_DEFINITION_ID("#{objectDefinitionId}"),
    OBJECT_DEFINITION_ID_1("#{objectDefinitionId1}"),
    OBJECT_DEFINITION_ID_2("#{objectDefinitionId2}"),
    OBJECT_DEFINITION_NAME("#{objectDefinitionName}"),
    OBJECT_DEFINITION_NAME_2("#{objectDefinitionName2}"),
    RELATIONSHIP_TYPE("#{relationshipType}"),
    RELATED_OBJECT_DEFINITION_NAME("#{relatedObjectDefinitionName}"),
    OBJECT_RELATIONSHIP_ID("#{objectRelationshipId}"),
    UNIVERSITY_1_ID("#{university1Id}"),
    UNIVERSITY_2_ID("#{university2Id}")
}