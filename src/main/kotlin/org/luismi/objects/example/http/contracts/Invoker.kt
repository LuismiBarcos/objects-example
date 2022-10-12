package org.luismi.objects.example.http.contracts

/**
 * @author Luis Miguel Barcos
 */
interface Invoker {
    fun invoke(endpoint:String, httpMethods: HTTPMethods, json: String?)
}