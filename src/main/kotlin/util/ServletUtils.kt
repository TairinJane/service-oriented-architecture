package util

import com.google.gson.GsonBuilder
import model.Route
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val gson = GsonBuilder()
    .registerTypeAdapterFactory(NullableTypAdapterFactory())
    .create()

fun <T> HttpServletResponse.writeJsonToBody(obj: T) {
    contentType = "application/json"
    characterEncoding = "UTF-8"
    writer?.run {
        print(gson.toJson(obj))
    }
}

fun HttpServletRequest.readBody(): String {
    val body = StringBuilder()
    var line = reader.readLine()
    while (line != null) {
        body.append(line, "\n")
        line = reader.readLine()
    }
    return body.toString()
}

fun <T> HttpServletRequest.getObjectFromBody(cl: Class<T>): T {
    val body = readBody()
    println("body: $body")
    return gson.fromJson(body, cl)
}

fun <T> Array<T>.paramArrayToString(): String {
    return joinToString("")
}

enum class SortType {
    ASC, DESC
}

val ALLOWED_PARAMETERS = listOf("sort", "limit", "offset") + Route.allFields

