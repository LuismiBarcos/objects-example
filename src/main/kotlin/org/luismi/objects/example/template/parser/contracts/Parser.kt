package org.luismi.objects.example.template.parser.contracts

/**
 * @author Luis Miguel Barcos
 */
interface Parser {
    fun parseText(text: String, replacements: Map<ParserConstants, String> ): String
}