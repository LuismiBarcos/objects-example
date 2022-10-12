package org.luismi.objects.example.asker.bussiness

import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.asker.validators.AskerValidators.answer

/**
 * @author Luis Miguel Barcos
 */
class AskerImpl: Asker {
    override fun askForInfo(msg: String): String {
        println(msg)
        val readLine = readLine() ?: ""

        val let = readLine.let(answer)

        return if (let.isSuccess) {
            let.getOrThrow()
        } else {
            throw let.exceptionOrNull()!!
        }
    }
}