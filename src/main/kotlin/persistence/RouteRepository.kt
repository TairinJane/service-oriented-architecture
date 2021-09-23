package persistence

import model.Route
import org.hibernate.Transaction
import util.HibernateSessionFactory

class RouteRepository {

    fun getRouteById(routeId: Long): Route {
        var transaction: Transaction? = null
        try {
            val session = HibernateSessionFactory.sessionFactory?.openSession()
            transaction = session?.beginTransaction()
            if (transaction == null || session == null)
                throw Exception("Couldn't create transaction")

            val route = session.find(Route::class.java, routeId)
            if (route != null) {
                transaction.commit()
                return route
            } else {
                throw Exception("No Route found with id = $routeId")
            }
        } catch (e: Exception) {
            transaction?.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun updateRoute(route: Route): Route {
        var transaction: Transaction? = null
        try {
            val session = HibernateSessionFactory.sessionFactory?.openSession()
            transaction = session?.beginTransaction()
            if (transaction == null || session == null)
                throw Exception("Couldn't create transaction")

            session.update(route)
            transaction.commit()
            return route
        } catch (e: Exception) {
            transaction?.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun addRoute(route: Route): Route {
        var transaction: Transaction? = null
        try {
            val session = HibernateSessionFactory.sessionFactory?.openSession()
            transaction = session?.beginTransaction()
            if (transaction == null || session == null)
                throw Exception("Couldn't create transaction")

            session.save(route)
            transaction.commit()
            return route
        } catch (e: Exception) {
            transaction?.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun deleteRoute(routeId: Long): Boolean {
        var transaction: Transaction? = null
        try {
            val session = HibernateSessionFactory.sessionFactory?.openSession()
            transaction = session?.beginTransaction()
            if (transaction == null || session == null)
                throw Exception("Couldn't create transaction")

            val route = session.find(Route::class.java, routeId)

            if (route != null) {
                session.delete(route)
                session.flush()
                transaction.commit()
            } else {
                transaction.rollback()
                throw Exception("No Route with id = $routeId")
            }
            return true
        } catch (e: Exception) {
            transaction?.rollback()
            e.printStackTrace()
            return false
        }
    }
}