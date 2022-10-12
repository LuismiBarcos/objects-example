package org.luismi.objects.example.creator.contracts

import org.luismi.objects.example.asker.contracts.AskerOptions

/**
 * @author Luis Miguel Barcos
 */
interface ObjectsCreator {
    fun createObjects(askerOptions: AskerOptions)
}