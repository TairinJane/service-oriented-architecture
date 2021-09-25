package persistence

import model.Route
import util.SortType
import javax.inject.Inject

class RouteService {

    @Inject
    private lateinit var routeRepository: RouteRepository

    fun getRouteById(routeId: Long): Route {
        return routeRepository.getRouteById(routeId)
    }

    fun newRoute(route: Route): Route {
        return routeRepository.addRoute(route)
    }

    fun updateRoute(route: Route): Route {
        return routeRepository.updateRoute(route)
    }

    fun deleteRoute(routeId: Long): Boolean {
        return routeRepository.deleteRoute(routeId)
    }

    fun filterRoutes(
        sorting: Map<String, SortType>,
        filter: Map<String, String>,
        limit: Int = 10,
        offset: Int = 0
    ): List<Route> {
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
}