package util

import com.example.model.Route
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val gson = GsonBuilder()
    .registerTypeAdapterFactory(NullableTypeAdapterFactory())
    .registerTypeAdapter(
        Date::class.java,
        JsonDeserializer { json, _, _ -> Date(json.asJsonPrimitive.asLong) })
    .registerTypeAdapter(
        Date::class.java,
        JsonSerializer<Date> { date, _, _ -> JsonPrimitive(date.time) })
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
    try {
        return gson.fromJson(body, cl)
    } catch (e: NumberFormatException) {
        if (e.message?.contains("to.x") == true) {
            throw IllegalArgumentException("Field 'to.x' must be an Integer")
        }
        throw e
    }
}

fun <T> Array<T>.paramArrayToString(): String {
    return joinToString("")
}

enum class SortType {
    ASC, DESC
}

val ALLOWED_PARAMETERS = listOf("sort", "limit", "offset") + Route.allFields

