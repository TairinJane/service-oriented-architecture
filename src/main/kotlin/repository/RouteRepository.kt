package repository

import model.Coordinates
import model.LocationFrom
import model.Route
import util.HibernateSessionFactory
import util.SortType
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class RouteRepository {

    private val threshold = 0.01

    companion object {
        var instance: RouteRepository = RouteRepository()
    }

    fun getRouteById(routeId: Int): Route {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val transaction = session.beginTransaction() ?: throw Exception("Couldn't create transaction")
        try {
            val route = session.find(Route::class.java, routeId)
            if (route != null) {
                transaction.commit()
                return route
            } else {
                throw NoSuchElementException("No Route found with id = $routeId")
            }
        } catch (e: Exception) {
            transaction.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun updateRoute(route: Route): Route {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val transaction = session.beginTransaction() ?: throw Exception("Couldn't create transaction")
        try {
            session.update(route)
            transaction.commit()
            return route
        } catch (e: Exception) {
            transaction.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun addRoute(route: Route): Route {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val transaction = session.beginTransaction() ?: throw Exception("Couldn't create transaction")

        try {
            session.save(route)
            transaction.commit()
            return route
        } catch (e: Exception) {
            transaction.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun deleteRoute(routeId: Int): Route {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val transaction = session.beginTransaction() ?: throw Exception("Couldn't create transaction")
        try {
            val route = session.find(Route::class.java, routeId)

            if (route != null) {
                session.delete(route)
                session.flush()
                transaction.commit()
            } else {
                throw NoSuchElementException("No Route found with id = $routeId")
            }
            return route
        } catch (e: Exception) {
            transaction.rollback()
            e.printStackTrace()
            throw e
        }
    }

    fun filterRoutes(
        sorting: Map<String, SortType>,
        filter: Map<String, Any>,
        limit: Int,
        offset: Int
    ): List<Route> {
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

    fun deleteWithDistanceEquals(distance: Float): Int {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val criteriaBuilder = session.criteriaBuilder
        val criteriaDelete = criteriaBuilder.createCriteriaDelete(Route::class.java)
        val root = criteriaDelete.from(Route::class.java)

        criteriaDelete.where(
            criteriaBuilder.between(
                root["distance"],
                distance - threshold,
                distance + threshold
            )
        )

        val transaction = session.beginTransaction()
        try {
            val deletedCount = session.createQuery(criteriaDelete).executeUpdate()
            transaction.commit()
            return deletedCount
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }

    fun findMinDistance(): Route {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val criteriaBuilder = session.criteriaBuilder

        val routeQuery = criteriaBuilder.createQuery(Route::class.java)
        val root = routeQuery.from(Route::class.java)

        val distanceQuery = routeQuery.subquery(Float::class.java)
        val distanceRoot = distanceQuery.from(Route::class.java)

        distanceQuery.select(criteriaBuilder.least(distanceRoot["distance"]))

        routeQuery.select(root).where(criteriaBuilder.equal(root.get<Float>("distance"), distanceQuery))

        val transaction = session.beginTransaction()
        try {
            val route = session.createQuery(routeQuery).singleResult
            transaction.commit()
            return route
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }

    fun countWithDistanceLessThan(distance: Float): Long {
        val session =
            HibernateSessionFactory.sessionFactory?.openSession() ?: throw Exception("Couldn't create session")
        val criteriaBuilder = session.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Long::class.java)
        val root = criteriaQuery.from(Route::class.java)

        criteriaQuery.select(criteriaBuilder.count(root))
        criteriaQuery.where(criteriaBuilder.lessThan(root["distance"], distance))

        val transaction = session.beginTransaction()
        try {
            val count = session.createQuery(criteriaQuery).singleResult
            transaction.commit()
            return count
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }

    private fun <T> CriteriaQuery<T>.applyFilter(
        cb: CriteriaBuilder,
        root: Root<T>,
        filter: Map<String, Any>
    ): CriteriaQuery<T> {
        if (filter.isEmpty()) return this

        val coordinatesJoin = root.join<Route, Coordinates>("coordinates")
        val fromJoin = root.join<Route, LocationFrom>("from")
        val toJoin = root.join<Route, LocationFrom>("to")

        val filterList = filter.entries.map { (fieldName, value) ->
            val (table, field) = if ('.' in fieldName) fieldName.split('.') else listOf("", fieldName)

            val tableRoot = when (table) {
                "coordinates" -> coordinatesJoin
                "from" -> fromJoin
                "to" -> toJoin
                else -> root
            }
            cb.equal(tableRoot.get<String>(field), value)
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

        val coordinatesJoin = root.join<Route, Coordinates>("coordinates")
        val fromJoin = root.join<Route, LocationFrom>("from")
        val toJoin = root.join<Route, LocationFrom>("to")

        val sortList = sorting.entries.map { (fieldName, sortType) ->
            val (table, field) = if ('.' in fieldName) fieldName.split('.') else listOf("", fieldName)

            val tableRoot = when (table) {
                "coordinates" -> coordinatesJoin
                "from" -> fromJoin
                "to" -> toJoin
                else -> root
            }

            val expression = when (field) {
                "name" -> tableRoot.get<String>(field)
                "creationDate" -> tableRoot.get<Date>(field)
                else -> tableRoot.get<Float>(field)
            }

            if (sortType == SortType.ASC) {
                cb.asc(expression)
            } else {
                cb.desc(expression)
            }
        }

        orderBy(sortList)
        return this
    }
}