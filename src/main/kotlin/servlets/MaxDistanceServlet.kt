package servlets

import persistence.RouteService
import util.writeJsonToBody
import javax.inject.Inject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "MaxDistance", value = ["/api/routes/min-distance"])
class MaxDistanceServlet : HttpServlet() {
    @Inject
    private lateinit var routeService: RouteService

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val route = routeService.findMinDistanceRoute()
        resp.writeJsonToBody(route)
    }
}