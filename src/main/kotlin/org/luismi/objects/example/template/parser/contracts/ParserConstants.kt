package org.luismi.objects.example.template.parser.contracts

/**
 * @author Luis Miguel Barcos
 */
enum class ParserConstants(val text: String) {
    NAME("#{name}"),
    PLURAL_NAME("#{pluralName}"),
    FIELD_NAME("#{fieldName}")
}