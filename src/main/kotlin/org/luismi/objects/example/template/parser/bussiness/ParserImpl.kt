package org.luismi.objects.example.template.parser.bussiness

import org.luismi.objects.example.template.parser.contracts.Parser
import org.luismi.objects.example.template.parser.contracts.ParserConstants

/**
 * @author Luis Miguel Barcos
 */
class ParserImpl: Parser {
    override fun parseText(text: String, replacements: Map<ParserConstants, String>): String {
        val iterator = replacements.iterator()

        return replace(iterator, iterator.next(), text)
    }

    private tailrec fun replace(
        iterator: Iterator<Map.Entry<ParserConstants, String>>,
        entry: Map.Entry<ParserConstants, String>,
        text: String
    ): String {
        if(iterator.hasNext()){
            return replace(iterator, iterator.next(), text.replace(entry.key.text, entry.value))
        }
        return text.replace(entry.key.text, entry.value)
    }
}