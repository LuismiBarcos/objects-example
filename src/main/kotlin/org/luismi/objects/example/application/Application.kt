package org.luismi.objects.example.application

import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.contracts.ObjectsCreator
import org.sdi.annotations.Component
import org.sdi.annotations.Inject

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [Application::class])
class Application {
    @Inject
    lateinit var asker: Asker
    @Inject
    lateinit var objectsCreator: ObjectsCreator

    fun init() {
        when(asker.askForInfo("Create Students as Custom Objects? (y/n)")){
            AskerOptions.YES -> objectsCreator.createObjects(AskerOptions.YES)
            AskerOptions.NO -> objectsCreator.createObjects(AskerOptions.NO)
        }
    }
}