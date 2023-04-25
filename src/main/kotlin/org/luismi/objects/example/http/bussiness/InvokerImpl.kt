package org.luismi.objects.example.http.bussiness

import org.luismi.objects.example.http.contracts.HTTPMethods
import org.luismi.objects.example.http.contracts.Invoker
import org.sdi.annotations.Component
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Luis Miguel Barcos
 */
@Component(classes = [Invoker::class])
class InvokerImpl: Invoker {
    override fun invoke(endpoint:String, httpMethods: HTTPMethods, json: String?): String {
        val serverURL: String = endpoint
        val url = URL(serverURL)
        val connection = url.openConnection() as HttpURLConnection

        connection.setRequestProperty("Authorization", "Basic dGVzdEBsaWZlcmF5LmNvbTp0ZXN0")

        return when(httpMethods) {
            HTTPMethods.GET -> doGet(connection)
            HTTPMethods.POST -> doPost(connection, httpMethods.method, json)
            HTTPMethods.DELETE -> doDelete(connection, httpMethods.method)
        }
    }

    private fun doPost(connection: HttpURLConnection, method: String, json: String?): String {
        connection.requestMethod = method
        connection.doOutput = true
        val postData: ByteArray = json!!.toByteArray()

        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-length", postData.size.toString())
        connection.setRequestProperty("Content-Type", "application/json")

        val dataOutputStream = DataOutputStream(connection.outputStream)
        dataOutputStream.write(postData)
        dataOutputStream.flush()

        return doRequest(connection)
    }

    private fun doGet(connection: HttpURLConnection): String = doRequest(connection)

    private fun doDelete(connection: HttpURLConnection, method: String): String {
        connection.requestMethod = method
        return doRequest(connection)
    }

    private fun doRequest(connection: HttpURLConnection): String {
        println("Requests sent\nResponse code: ${connection.responseCode}")

        return connection.inputStream.bufferedReader().readText()
    }
}
