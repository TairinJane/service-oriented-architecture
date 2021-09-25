package servlets

import persistence.RouteService
import util.paramArrayToString
import javax.inject.Inject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "DeleteWithDistance", value = ["/api/routes/delete-with-distance"])
class DeleteServlet : HttpServlet() {
    @Inject
    private lateinit var routeService: RouteService

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        val distanceString = req.parameterMap["distance"]?.paramArrayToString()
            ?: throw IllegalArgumentException("Parameter 'distance' is required")

        try {
            val distance = distanceString.toFloat()
            routeService.deleteWithDistanceEquals(distance)
            resp.status = 204
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'distance' = $distanceString is not a valid float number")
        }
    }
}