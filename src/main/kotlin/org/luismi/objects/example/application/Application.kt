package org.luismi.objects.example.application

import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.manage.contracts.ObjectsCreator
import org.luismi.objects.example.manage.contracts.ObjectsDeleter
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
    @Inject
    lateinit var objectsDeleter: ObjectsDeleter

    fun init() {
        when(asker.askForInfo("Do you want to create some Objects? (y/n)")) {
            AskerOptions.YES -> createObjects()
            AskerOptions.NO -> deleteObjects()
        }
    }

    private fun createObjects() {
        when(asker.askForInfo("Create Students as Custom Objects? (y/n)")) {
            AskerOptions.YES -> objectsCreator.createObjects(AskerOptions.YES)
            AskerOptions.NO -> objectsCreator.createObjects(AskerOptions.NO)
        }
    }

    private fun deleteObjects() {
        when(asker.askForInfo("Do you want to delete all objects? (y/n)")) {
            AskerOptions.YES -> objectsDeleter.deleteAllCustomObjectsDefinitions()
            AskerOptions.NO -> println("Bye Bye!")
        }
    }
}