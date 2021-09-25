package servlets

import persistence.RouteService
import util.paramArrayToString
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "CountShorter", value = ["/api/routes/count-shorter-than"])
class CountShorterServlet : HttpServlet() {

    private val routeService = RouteService.instance

    //?distance=...
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val distanceString = req.parameterMap["distance"]?.paramArrayToString()
            ?: throw IllegalArgumentException("Parameter 'distance' is required")

        try {
            val distance = distanceString.toFloat()
            val count = routeService.countWithDistanceLessThan(distance)
            resp.writer.println(count)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'distance' = $distanceString is not a valid float number")
        }
    }
}