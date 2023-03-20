package org.luismi.objects.example

import org.luismi.objects.example.application.Application
import org.sdi.injector.SimpleDependencyInjector

/**
 * @author Luis Miguel Barcos
 */

private class Main

fun main() {
    // Use SDI
    val dependencyInjector = SimpleDependencyInjector()
    dependencyInjector.init(Main::class.java)

    //Get application
    val application = dependencyInjector.getService(Application::class.java)

    //Start application
    application.init()
}
