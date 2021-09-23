package servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/api/routes/delete-with-distance"])
class DeleteServlet: HttpServlet() {
    override fun doDelete(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doDelete(req, resp)
    }
}