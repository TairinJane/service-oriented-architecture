package servlets

import model.Route
import services.RouteService
import util.*
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Main", value = ["/api/routes/*"])
class BaseServlet : HttpServlet() {

    private val routeService = RouteService.instance

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
            // http://localhost:8080/api/routes?sort=distance&distance=5

            //TODO: вынести это в сервис, передавать туда параметр мапу

            val respBody = resp.writer
            val params = req.parameterMap //эту
            params.entries.forEach { (k, v) ->
                respBody.println("$k = ${v.contentToString()}")
            }
            respBody.println()

            req.checkAllowedParameters()

            val filter = parseFilterMap(params.filter { (key, _) -> key in Route.allFields })
            respBody.println("== Filter")
            respBody.println(filter.toString())

            val sorting = params["sort"]?.let { parseSortingMap(it) } ?: mapOf()
            respBody.println("== Sorting")
            respBody.println(sorting.toString())

            //TODO: check NumberFormatException message for var name
            val limit = params["limit"]?.paramArrayToString()?.toInt() ?: 10
            val offset = params["offset"]?.paramArrayToString()?.toInt() ?: 0

            val filteredRoutes = routeService.filterRoutes(sorting, filter, limit, offset)
            resp.writeJsonToBody(filteredRoutes)
        }
    }

    //new route - get params from body
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val newRoute = req.getObjectFromBody(Route::class.java)
        println("route to add: $newRoute")
        val route = routeService.newRoute(newRoute)
        resp.writeJsonToBody(route)
        resp.status = 201
    }

    //update route
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        val routeToUpdate = req.getObjectFromBody(Route::class.java)
        println("route to update: $routeToUpdate")
        val route = routeService.updateRoute(routeToUpdate)
        resp.writeJsonToBody(route)
    }

    //{id} - delete by id
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val id = req.pathInfo?.toLong() ?: throw IllegalArgumentException("Parameter 'id' is required")
            val result = routeService.deleteRoute(id)
            resp.writeJsonToBody(result)
            resp.status = 204
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'id' is not a valid number")
        }

    }

    private fun parseSortingMap(sortingArray: Array<String>): Map<String, SortType> {
        val sorting = mutableMapOf<String, SortType>()
        sortingArray.forEach {
            val sotType = if (it[0] == '-') SortType.DESC else SortType.ASC
            val field = it.removePrefix("-")

            if (field !in Route.allFields) throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

            sorting[field] = sotType
        }
        return sorting
    }

    private fun parseFilterMap(params: Map<String, Array<String>>): Map<String, Any> {
        if (params.isEmpty()) return mapOf()

        val filter = mutableMapOf<String, Any>()
        params.forEach { (key, value) ->
            val baseParam = key.split('.')[0]
            if (baseParam !in Route.allFields) throw IllegalArgumentException("Filter parameter '$key' is not allowed")
            else {
                val valueString = value.paramArrayToString()
                try {
                    filter[key] = when (key) {
                        "name" -> valueString
                        "distance" -> valueString.toFloat()
                        "creationDate" -> LocalDateTime.parse(valueString)
                        else -> valueString
                    }
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException("Parameter '$key' = $valueString is not a valid number")
                } catch (e: DateTimeParseException) {
                    throw IllegalArgumentException("Parameter '$key' = $valueString is not a valid date")
                }
            }
        }
        return filter
    }

    private fun HttpServletRequest.checkAllowedParameters() {
        parameterNames.toList().forEach {
            if (it !in ALLOWED_PARAMETERS) throw IllegalArgumentException("Parameter $it is not allowed")
        }
    }
}