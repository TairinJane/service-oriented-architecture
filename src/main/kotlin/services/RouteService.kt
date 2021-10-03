package services

import model.Route
import repository.RouteRepository
import util.SortType
import util.paramArrayToString
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class RouteService {

    companion object {
        var instance: RouteService = RouteService()
    }

    private val routeRepository = RouteRepository.instance

    fun getRouteById(routeId: Long): Route {
        return routeRepository.getRouteById(routeId)
    }

    fun newRoute(route: Route): Route {
        route.checkConstraints()
        return routeRepository.addRoute(route)
    }

    fun updateRoute(route: Route): Route {
        route.checkConstraints()
        return routeRepository.updateRoute(route)
    }

    fun deleteRoute(routeId: Long): Route {
        return routeRepository.deleteRoute(routeId)
    }

    fun filterRoutes(params: Map<String, Array<String>>): List<Route> {
        val filter = parseFilterMap(params.filter { (key, _) -> key in Route.allFields })
        println("== Filter")
        println(filter.toString())

        val sorting = params["sort"]?.let { parseSortingMap(it) } ?: mapOf()
        println("== Sorting")
        println(sorting.toString())

        //TODO: check NumberFormatException message for var name
        val limit = params["limit"]?.paramArrayToString()?.toInt() ?: 10
        val offset = params["offset"]?.paramArrayToString()?.toInt() ?: 0

        return routeRepository.filterRoutes(sorting, filter, limit, offset)
    }

    fun deleteWithDistanceEquals(distance: Float): Boolean {
        return routeRepository.deleteWithDistanceEquals(distance)
    }

    fun findMinDistanceRoute(): Route {
        return routeRepository.findMinDistance()
    }

    fun countWithDistanceLessThan(distance: Float): Long {
        return routeRepository.countWithDistanceLessThan(distance)
    }

    private fun parseSortingMap(sortingArray: Array<String>): Map<String, SortType> {
        val sorting = mutableMapOf<String, SortType>()
        sortingArray.forEach {
            val sotType = if (it[0] == '-') SortType.DESC else SortType.ASC
            val field = it.removePrefix("-")

            if (field !in Route.allFields) throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

            sorting[field] = sotType
        }
        return sorting
    }

    private fun parseFilterMap(params: Map<String, Array<String>>): Map<String, Any> {
        if (params.isEmpty()) return mapOf()

        val filter = mutableMapOf<String, Any>()
        params.forEach { (key, value) ->
            val baseParam = key.split('.')[0]
            if (baseParam !in Route.allFields) throw IllegalArgumentException("Filter parameter '$key' is not allowed")
            else {
                val valueString = value.paramArrayToString()
                try {
                    filter[key] = when (key) {
                        "name" -> valueString
                        "distance" -> valueString.toFloat()
                        "creationDate" -> LocalDateTime.parse(valueString)
                        else -> valueString
                    }
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException("Parameter '$key' = $valueString is not a valid number")
                } catch (e: DateTimeParseException) {
                    throw IllegalArgumentException("Parameter '$key' = $valueString is not a valid date")
                }
            }
        }
        return filter
    }
}