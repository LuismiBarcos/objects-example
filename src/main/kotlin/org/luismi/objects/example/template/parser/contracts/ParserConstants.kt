package org.luismi.objects.example.template.parser.contracts

/**
 * @author Luis Miguel Barcos
 */
enum class ParserConstants(val text: String) {
    NAME("#{name}"),
    PLURAL_NAME("#{pluralName}"),
    FIELD_NAME("#{fieldName}"),
    RELATIONSHIP_NAME("#{relationshipName}"),
    OBJECT_DEFINITION_ID_1("#{objectDefinitionId1}"),
    OBJECT_DEFINITION_ID_2("#{objectDefinitionId2}"),
    OBJECT_DEFINITION_NAME_2("#{objectDefinitionName2}"),
    RELATIONSHIP_TYPE("#{relationshipType}")
}