package persistence

import java.sql.Connection
import java.sql.SQLException
import javax.servlet.http.HttpSessionBindingEvent
import javax.servlet.http.HttpSessionBindingListener


class SessionConnection(var connection: Connection? = null) : HttpSessionBindingListener {

    override fun valueBound(event: HttpSessionBindingEvent) {
        if (connection != null) {
            println("Binding a valid connection")
        } else {
            println("Binding a null connection")
        }
    }

    override fun valueUnbound(event: HttpSessionBindingEvent) {
        if (connection != null) {
            println(
                "Closing the bound connection as the session expires"
            )
            try {
                connection!!.close()
            } catch (ignore: SQLException) {
            }
        }
    }
}