package servlets

import persistence.SessionConnection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession


@WebServlet(name = "Home", value = ["/hello"])
class Servlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {

        val session: HttpSession = req.session
        var sessionConnection = session.getAttribute("sessionConnection") as? SessionConnection
        var connection = sessionConnection?.connection

        res.contentType = "text/html"
        res.writer.run {
            print("<html><body>")
            print("<h3>Hi shit</h3>")
            print("</body></html>")
        }

        if (connection == null) {
            try {
                val properties = getConnectionProperties()
                connection = DriverManager.getConnection(
                    properties["url"], properties["user"], properties["password"]
                )
            } catch (e: SQLException) {
                println("Connection fail: " + e.message)
            }
            if (connection != null) {
                sessionConnection = SessionConnection()
                sessionConnection.connection = connection
                session.setAttribute("sessionConnection", sessionConnection)
            }
        }
    }

    private fun getConnectionProperties(): MutableMap<String, String> {
        val properties = mutableMapOf<String, String>()
        servletContext.getResourceAsStream("WEB-INF\\classes\\config.properties").use { input ->
            val config = Properties()
            config.load(input)

            properties["url"] = config.getProperty("db.url")
            properties["user"] = config.getProperty("db.user")
            properties["password"] = config.getProperty("db.password")
        }
        return properties
    }
}