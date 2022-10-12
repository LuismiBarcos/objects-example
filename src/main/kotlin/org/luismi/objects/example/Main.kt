package org.luismi.objects.example

import org.luismi.objects.example.asker.bussiness.AskerImpl
import org.luismi.objects.example.asker.contracts.Asker
import org.luismi.objects.example.dependency.injector.DependencyInjector

/**
 * @author Luis Miguel Barcos
 */
fun main() {
    DependencyInjector.addDependency(Asker::class, AskerImpl())

    val asker = DependencyInjector.getDependency<Asker>(Asker::class)

    val askForInfo = asker.askForInfo("Create Students as Custom Objects? (y/n)")

    println(askForInfo)
}
