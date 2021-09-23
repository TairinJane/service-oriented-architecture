package servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/api/routes/count-shorter-than"])
class CountShorterServlet: HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {

    }
}