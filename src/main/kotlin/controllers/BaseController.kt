package controllers

import model.Coordinates
import model.LocationFrom
import model.LocationTo
import model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import services.RouteService
import java.util.*


@RestController("/api/routes/*")
class BaseController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping("/{id}")
    fun getRouteById(@PathVariable id: Int): Route {
        return routeService.getRouteById(id)
    }

    @GetMapping
    fun getFilteredSortedRoutes(
        @RequestParam(required = false) sort: List<String>,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false) name: String,
        @RequestParam(required = false) distance: Float,
        @RequestParam(required = false) id: Int,
        @RequestParam(required = false) creationDate: Date,
        @RequestParam(name = "coordinates.x", required = false) coordinatesX: Float,
        @RequestParam(name = "coordinates.y", required = false) coordinatesY: Float,
        @RequestParam(name = "coordinates.id", required = false) coordinatesId: Int,
        @RequestParam(name = "from.x", required = false) fromX: Float,
        @RequestParam(name = "from.y", required = false) fromY: Float,
        @RequestParam(name = "from.name", required = false) fromName: String,
        @RequestParam(name = "from.id", required = false) fromId: Int,
        @RequestParam(name = "to.x", required = false) toX: Int,
        @RequestParam(name = "to.y", required = false) toY: Double,
        @RequestParam(name = "to.z", required = false) toZ: Double,
        @RequestParam(name = "to.id", required = false) toId: Int,
    ): List<Route> {
        val route = Route(
            name = name,
            coordinates = Coordinates(coordinatesX, coordinatesY, coordinatesId),
            from = LocationFrom(fromX, fromY, fromName, fromId),
            to = LocationTo(toX, toY, toZ, toId),
            distance = distance
        )
        return routeService.filterRoutes(sort, limit, offset, route)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun newRoute(@RequestBody route: Route): Route {
        return routeService.newRoute(route)
    }

    @PutMapping("/{id}")
    fun updateRoute(@PathVariable id: Int, @RequestBody route: Route): Route {
        if (route.id != id) throw IllegalArgumentException("Path parameter 'id' is different from entity id")
        return routeService.updateRoute(route)
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: Int) {
        return routeService.deleteRoute(id)
    }
}