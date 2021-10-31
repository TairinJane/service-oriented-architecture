package navigator.service

import common.model.Coordinates
import common.model.Route
import navigator.client.RoutesClient

class NavigatorService {

    private val client = RoutesClient.instance

    companion object {
        val instance = NavigatorService()
    }

    fun findShortestRouteBetween(fromId: Int, toId: Int): Route? {
        val routes = client.findRoutesByLocations(fromId = fromId, toId = toId)
        return routes.minByOrNull { it.distance ?: Float.MAX_VALUE }
    }

    fun newRouteBetween(fromId: Int, toId: Int, distance: Float): Route? {
        val fromRoute = client.findRoutesByLocations(fromId = fromId, limit = 1)
        if (fromRoute.isEmpty()) throw NoSuchElementException("Location (from) with id = $fromId doesn't exist")

        val toRoute = client.findRoutesByLocations(toId = toId, limit = 1)
        if (toRoute.isEmpty()) throw NoSuchElementException("Location (to) with id = $toId doesn't exist")

        val from = fromRoute[0].from
        val to = toRoute[0].to

        val newRoute = Route(
            name = "Route from $fromId to $toId",
            coordinates = Coordinates(x = fromId.toFloat(), y = toId.toFloat(), id = null),
            from = from,
            to = to,
            distance = distance,
            creationDate = null,
            id = null
        )

        return client.newRoute(newRoute)

    }
}