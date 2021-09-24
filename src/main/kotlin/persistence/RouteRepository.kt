package persistence

import model.Route
import org.hibernate.Transaction
import servlets.SortType
import util.HibernateSessionFactory
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root


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

    fun filterRoutes(sorting: Map<String, SortType>, filter: Map<String, String>, limit: Int, offset: Int): List<Route> {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val criteriaBuilder = session.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Route::class.java)

        val root = criteriaQuery.from(Route::class.java)
        criteriaQuery.select(root)
        criteriaQuery.applyFilter(criteriaBuilder, root, filter)
        criteriaQuery.applySorting(criteriaBuilder, root, sorting)

        val query = session.createQuery(criteriaQuery)
        query.firstResult = offset
        query.maxResults = limit

        val results = query.resultList
        println(results)

        return results
    }

    private fun <T> CriteriaQuery<T>.applyFilter(
        cb: CriteriaBuilder,
        root: Root<T>,
        filter: Map<String, String>
    ): CriteriaQuery<T> {
        if (filter.isEmpty()) return this

        val filterList = filter.entries.map { (field, value) ->
            cb.equal(root.get<String>(field), value)
        }

        where(*filterList.toTypedArray())
        return this
    }

    private fun <T> CriteriaQuery<T>.applySorting(
        cb: CriteriaBuilder,
        root: Root<T>,
        sorting: Map<String, SortType>
    ): CriteriaQuery<T> {
        if (sorting.isEmpty()) return this

        val sortList = sorting.entries.map { (field, sortType) ->
            if (sortType == SortType.ASC) {
                cb.asc(root.get<String>(field))
            } else {
                cb.desc(root.get<String>(field))
            }
        }

        orderBy(sortList)
        return this
    }
}