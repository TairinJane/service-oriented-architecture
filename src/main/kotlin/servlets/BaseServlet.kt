package servlets

import model.Route
import services.RouteService
import util.ALLOWED_PARAMETERS
import util.getObjectFromBody
import util.writeJsonToBody
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Main", value = ["/api/routes/*"])
class BaseServlet : HttpServlet() {

    private val routeService = RouteService.instance

    //{id} or sorting
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val id = req.pathInfo?.removePrefix("/")?.toInt()

            if (id != null) {
                val route = routeService.getRouteById(id)
                resp.writeJsonToBody(route)

            } else {
                req.checkAllowedParameters()
                val params = req.parameterMap

                val filteredRoutes = routeService.filterRoutes(params)
                resp.writeJsonToBody(filteredRoutes)
            }
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'id' is not a valid number")
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
            val id = req.pathInfo?.removePrefix("/")?.toInt()
                ?: throw IllegalArgumentException("Parameter 'id' is required")
            val result = routeService.deleteRoute(id)
            resp.writeJsonToBody(result)
            resp.status = 204
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'id' is not a valid number")
        }

    }

    private fun HttpServletRequest.checkAllowedParameters() {
        parameterNames.toList().forEach {
            if (it !in ALLOWED_PARAMETERS) throw IllegalArgumentException("Parameter $it is not allowed")
        }
    }
}