package servlets

import com.google.gson.Gson
import model.Route
import persistence.RouteService
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
        try {
            val id = req.pathInfo?.toLong()
            if (id != null) {
                val route = routeService.getRouteById(id)
                resp.writeRouteToBody(route)
            } else {
                resp.sendError(400, "Id parameter is not a valid number")
            }
        } catch (e: Exception) {
            resp.sendError(400, e.message)
        }
    }

    //new route - get params from body
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val body = req.readBody()
        val newRoute = gson.fromJson(body, Route::class.java)
        val route = routeService.newRoute(newRoute)
        resp.writeRouteToBody(route)
    }

    //update route
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        val body = req.readBody()
        val routeToUpdate = gson.fromJson(body, Route::class.java)
        val route = routeService.updateRoute(routeToUpdate)
        resp.writeRouteToBody(route)
    }

    //{id} - delete by id
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val id = req.pathInfo?.toLong()
            if (id != null) {
                val result = routeService.deleteRoute(id)
                if (!result) {
                    resp.sendError(500, "Server Error: couldn't delete Route with id = $id")
                }
            } else {
                resp.sendError(400, "Id parameter is not a valid number")
            }
        } catch (e: Exception) {
            resp.sendError(400, e.message)
        }
    }

    override fun service(req: HttpServletRequest?, resp: HttpServletResponse?) {
        //TODO: cors
        super.service(req, resp)
    }

    private fun HttpServletResponse.writeRouteToBody(route: Route) {
        contentType = "application/json"
        characterEncoding = "UTF-8"
        writer?.run {
            print(gson.toJson(route))
            flush()
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
}