package org.luismi.objects.example

import org.luismi.objects.example.asker.bussiness.AskerImpl
import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.asker.contracts.AskerOptions
import org.luismi.objects.example.creator.bussiness.ObjectsCreatorImpl
import org.luismi.objects.example.creator.contracts.ObjectsCreator
import org.luismi.objects.example.dependency.injector.DependencyInjector
import org.luismi.objects.example.http.bussiness.InvokerImpl
import org.luismi.objects.example.http.contracts.Invoker
import org.luismi.objects.example.template.parser.bussiness.ParserImpl
import org.luismi.objects.example.template.parser.contracts.Parser

/**
 * @author Luis Miguel Barcos
 */
fun main() {
    //Inject dependencies
    DependencyInjector.addDependency(Asker::class, AskerImpl())
    DependencyInjector.addDependency(Parser::class, ParserImpl())
    DependencyInjector.addDependency(Invoker::class, InvokerImpl())
    DependencyInjector.addDependency(ObjectsCreator::class, ObjectsCreatorImpl())

    // Get implementations
    val asker = DependencyInjector.getDependency<Asker>(Asker::class)
    val objectsCreator = DependencyInjector.getDependency<ObjectsCreator>(ObjectsCreator::class)

    // Main code
    when(asker.askForInfo("Create Students as Custom Objects? (y/n)")){
        AskerOptions.YES -> objectsCreator.createObjects(AskerOptions.YES)
        AskerOptions.NO -> objectsCreator.createObjects(AskerOptions.NO)
    }
}
