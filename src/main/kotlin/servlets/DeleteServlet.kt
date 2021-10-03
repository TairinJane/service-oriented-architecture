package servlets

import services.RouteService
import util.paramArrayToString
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "DeleteWithDistance", value = ["/api/routes/delete-with-distance"])
class DeleteServlet : HttpServlet() {

    private val routeService = RouteService.instance

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        val distanceString = req.parameterMap["distance"]?.paramArrayToString()
            ?: throw IllegalArgumentException("Parameter 'distance' is required")

        try {
            val distance = distanceString.toFloat()
            val deletedCount = routeService.deleteWithDistanceEquals(distance)
            if (deletedCount > 0) {
                resp.writer.println(deletedCount)
                resp.status = 204
            } else throw IllegalArgumentException("No routes with distance = $distance were deleted")
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Parameter 'distance' = $distanceString is not a valid float number")
        }
    }
}