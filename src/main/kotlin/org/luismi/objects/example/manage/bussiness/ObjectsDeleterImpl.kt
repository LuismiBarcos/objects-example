package org.luismi.objects.example.manage.bussiness

import org.luismi.objects.example.manage.contracts.ObjectDefinitionService
import org.luismi.objects.example.manage.contracts.ObjectRelationshipService
import org.luismi.objects.example.manage.contracts.ObjectsDeleter
import org.sdi.annotations.Component
import org.sdi.annotations.Inject

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [ObjectsDeleter::class])
class ObjectsDeleterImpl: ObjectsDeleter {

    @Inject
    lateinit var objectDefinitionService: ObjectDefinitionService
    @Inject
    lateinit var objectRelationshipService: ObjectRelationshipService

    override fun deleteAllCustomObjectsDefinitions() {
        val allCustomObjectDefinitions = objectDefinitionService.getAllCustomObjectDefinitions()

        allCustomObjectDefinitions.forEach(objectRelationshipService::deleteCustomObjectDefinitionRelationships)
        allCustomObjectDefinitions.forEach(objectDefinitionService::deleteCustomObjectDefinition)


    }
}