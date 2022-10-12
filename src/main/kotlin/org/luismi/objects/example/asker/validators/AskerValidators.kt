package org.luismi.objects.example.asker.validators

import org.luismi.objects.example.validator.Validator
import org.luismi.objects.example.validator.orElseFail
import org.luismi.objects.example.validator.validate
import java.lang.Exception

/**
 * @author Luis Miguel Barcos
 */

object AskerValidators {
    val answer: Validator<String> =
        validate<String> {
            when(it.lowercase()) {
                "yes" -> true
                "y" -> true
                "no" -> true
                "n" -> true
                else -> false
            }
        }
        .orElseFail(Exception("Please, enter a valid string.\nThe valid response are:\n\tyes or y\n\tno or n"))
}