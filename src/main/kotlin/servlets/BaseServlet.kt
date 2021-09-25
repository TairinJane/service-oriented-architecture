package servlets

import com.google.gson.Gson
import model.Route
import persistence.RouteService
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException
import javax.inject.Inject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Home", value = ["/api/routes/*"])
class BaseServlet : HttpServlet() {

    @Inject
    private lateinit var routeService: RouteService

    private val gson = Gson()

    //{id} or sorting
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val id = req.pathInfo?.toLong()
        if (id != null) {
            //get by id
            val route = routeService.getRouteById(id)
            resp.writeJsonToBody(route)
        } else {
            //sorting stuff
            // http://localhost:8080/api/routes?sort=a&sort=-cccc&name=b

            //TODO: вынести это в сервис, передавать туда параметр мапу

            val respBody = resp.writer
            val params = req.parameterMap //эту
            params.entries.forEach { (k, v) ->
                respBody.println("$k = ${v.contentToString()}")
            }
            respBody.println()

            val filter = mutableMapOf<String, String>()
            params.forEach { (key, value) ->
                val isRouteField = ALLOWED_FIELDS[key]
                if (isRouteField == null) {
                    throw IllegalArgumentException("Parameter '$key' is not allowed")
                } else {
                    if (isRouteField)
                        filter[key] = value.paramArrayToString()
                }
            }
            respBody.println("== Filter")
            respBody.println(filter.toString())

            val sorting = mutableMapOf<String, SortType>()
            params["sort"]?.forEach {
                if (it[0] == '-') {
                    sorting[it.removePrefix("-")] = SortType.DESC
                } else sorting[it] = SortType.ASC
            }
            respBody.println("== Sorting")
            respBody.println(sorting.toString())

            val limit = params["limit"]?.paramArrayToString()?.toInt() ?: 10
            val offset = params["offset"]?.paramArrayToString()?.toInt() ?: 0

            val filteredRoutes = routeService.filterRoutes(sorting, filter, limit, offset)
            resp.writeJsonToBody(filteredRoutes)
        }
    }

    //new route - get params from body
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val body = req.readBody()
        val newRoute = gson.fromJson(body, Route::class.java)
        val route = routeService.newRoute(newRoute)
        resp.writeJsonToBody(route)
        resp.status = 201
    }

    //update route
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        val body = req.readBody()
        val routeToUpdate = gson.fromJson(body, Route::class.java)
        val route = routeService.updateRoute(routeToUpdate)
        resp.writeJsonToBody(route)
    }

    //{id} - delete by id
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        val id = req.pathInfo?.toLong()
        if (id != null) {
            val result = routeService.deleteRoute(id)
            if (!result) {
                throw NoSuchElementException("Couldn't delete Route with id = $id")
            }
        } else {
            throw IllegalArgumentException("Id parameter is absent")
        }
        resp.status = 204
    }

    private fun <T> HttpServletResponse.writeJsonToBody(obj: T) {
        contentType = "application/json"
        characterEncoding = "UTF-8"
        writer?.run {
            print(gson.toJson(obj))
        }
    }

    private fun HttpServletRequest.readBody(): String {
        val body = StringBuilder()
        var line = reader.readLine()
        while (line != null) {
            body.append(line, "\n")
            line = reader.readLine()
        }
        return body.toString()
    }

    private fun <T> Array<T>.paramArrayToString(): String {
        return joinToString("")
    }
}