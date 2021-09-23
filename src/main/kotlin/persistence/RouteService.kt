package persistence

import model.Route
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
}