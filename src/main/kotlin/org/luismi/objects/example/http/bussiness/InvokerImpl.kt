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
    override fun invoke(endpoint:String, httpMethods: HTTPMethods, json: String?): String {
        val serverURL: String = endpoint
        val url = URL(serverURL)
        val connection = url.openConnection() as HttpURLConnection
        return when(httpMethods) {
            HTTPMethods.GET -> doGet(connection)
            HTTPMethods.POST -> doPost(connection, httpMethods.method, json)
        }
    }

    private fun doPost(connection: HttpURLConnection, method: String, json: String?): String {
        connection.requestMethod = method
        connection.doOutput = true
        val postData: ByteArray = json!!.toByteArray()

        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-lenght", postData.size.toString())
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Basic dGVzdEBsaWZlcmF5LmNvbTp0ZXN0")

        return doRequest(connection, postData)
    }

    private fun doGet(connection: HttpURLConnection): String {
        TODO("Not yet implemented")
    }

    private fun doRequest(connection: HttpURLConnection, postData: ByteArray): String {
        val dataOutputStream = DataOutputStream(connection.outputStream)
        dataOutputStream.write(postData)
        dataOutputStream.flush()

        println("Requests sent\nResponse code: ${connection.responseCode}")

        return connection.inputStream.bufferedReader().readText()
//        return if (connection.responseCode == HttpURLConnection.HTTP_OK) {
//            connection.inputStream.bufferedReader().readText()
//        } else {
//            connection.errorStream.bufferedReader().readText()
//    //            val context = JsonPath.parse(data)
//    //            val status = context.read<String>("status")
//    //            println("RESULT --> $status")
//        }

    }
}
