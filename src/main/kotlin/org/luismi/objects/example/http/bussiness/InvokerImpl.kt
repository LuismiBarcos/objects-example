package org.luismi.objects.example.http.bussiness

import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.Invoker
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Luis Miguel Barcos
 */
class InvokerImpl: Invoker {
    override fun invoke(endpoint:String, httpMethods: HTTPMethods, json: String?) {
        val serverURL: String = endpoint
        val url = URL(serverURL)
        val connection = url.openConnection() as HttpURLConnection
        when(httpMethods) {
            HTTPMethods.GET -> doGet(connection)
            HTTPMethods.POST -> doPost(connection, httpMethods.method, json)
        }
    }

    private fun doPost(connection: HttpURLConnection, method: String, json: String?) {
        connection.requestMethod = method
        connection.doOutput = true
        val postData: ByteArray = json!!.toByteArray()

        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-lenght", postData.size.toString())
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Basic dGVzdEBsaWZlcmF5LmNvbTp0ZXN0")

        doRequest(connection, postData)

    }

    private fun doGet(connection: HttpURLConnection) {
        TODO("Not yet implemented")
    }

    private fun doRequest(connection: HttpURLConnection, postData: ByteArray) {
        val dataOutputStream = DataOutputStream(connection.outputStream)
        dataOutputStream.write(postData)
        dataOutputStream.flush()

        println("Requests sent\nResponse code: ${connection.responseCode}")
    }
}
