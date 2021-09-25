package servlets

import model.Route
import persistence.RouteService
import util.*
import javax.inject.Inject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Main", value = ["/api/routes/*"])
class BaseServlet : HttpServlet() {

    @Inject
    private lateinit var routeService: RouteService

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
                val isRouteField =
                    ALLOWED_FIELDS[key] ?: throw IllegalArgumentException("Parameter '$key' is not allowed")
                if (isRouteField)
                    filter[key] = value.paramArrayToString()
            }
            respBody.println("== Filter")
            respBody.println(filter.toString())

            val sorting = mutableMapOf<String, SortType>()
            params["sort"]?.forEach {
                val sotType = if (it[0] == '-') SortType.DESC else SortType.ASC
                val field = it.removePrefix("-")

                ALLOWED_FIELDS[field] ?: throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

                sorting[field] = sotType
            }
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
        val route = routeService.newRoute(newRoute)
        resp.writeJsonToBody(route)
        resp.status = 201
    }

    //update route
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        val routeToUpdate = req.getObjectFromBody(Route::class.java)
        val route = routeService.updateRoute(routeToUpdate)
        resp.writeJsonToBody(route)
    }

    //{id} - delete by id
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val id = req.pathInfo?.toLong() ?: throw IllegalArgumentException("Parameter 'id' is required")
            val result = routeService.deleteRoute(id)
            if (!result) {
                throw NoSuchElementException("Couldn't delete Route with id = $id")
            }
            resp.status = 204
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'id' is not a valid number")
        }

    }
}