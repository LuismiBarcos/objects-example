package org.luismi.objects.example.asker.bussiness

import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.asker.validators.AskerValidators.answer

/**
 * @author Luis Miguel Barcos
 */
class AskerImpl: Asker {
    override fun askForInfo(msg: String): AskerOptions {
        println(msg)
        val readLine = readLine() ?: ""

        return readLine.let(answer).map {
            when (it.lowercase().contains("y")) {
                true -> AskerOptions.YES
                false -> AskerOptions.NO
            }
        }.getOrElse { throw it }
    }
}