package services

import model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import repository.RouteRepository
import util.LimitOffsetPagination
import util.SortType

@Service
class RouteService {

    @Autowired
    private lateinit var routeRepository: RouteRepository

    fun getRouteById(routeId: Int): Route {
        return routeRepository.findById(routeId).orElseThrow { NoSuchElementException("No route with id = $routeId") }
    }

    fun newRoute(route: Route): Route {
        route.checkConstraints()
        return routeRepository.save(route)
    }

    fun updateRoute(route: Route): Route {
        route.checkConstraints()
        return routeRepository.save(route)
    }

    fun deleteRoute(routeId: Int) {
        return routeRepository.deleteById(routeId)
    }

    fun filterRoutes(sortList: List<String>, limit: Int, offset: Int, filterRoute: Route): List<Route> {
        val routeExample = Example.of(filterRoute)
        val sorting = parseSorting(sortList)
        val pagination = LimitOffsetPagination(offset, limit, sorting)

        return routeRepository.findAll(routeExample, pagination)
    }

    fun deleteWithDistanceEquals(distance: Float): Int {
        return routeRepository.deleteByDistanceEquals(distance)
    }

    fun findMinDistanceRoute(): Route {
        return routeRepository.findWithMinDistance()
    }

    fun countWithDistanceLessThan(distance: Float): Int {
        return routeRepository.countByDistanceLessThan(distance)
    }

    private fun parseSorting(sortingList: List<String>): Sort {
        val sorting = Sort.unsorted()
        sortingList.forEach {
            val sortType = if (it[0] == '-') SortType.DESC else SortType.ASC
            val field = it.removePrefix("-")

            if (field !in Route.allFields) throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

            val sort = Sort.by(field)
            if (sortType == SortType.DESC) sort.descending()

            sorting.and(sort)
        }
        return sorting
    }
}