package com.example.controllers

import com.example.model.Route
import com.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/routes")
class RoutesController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping("/{id}")
    fun getRouteById(@PathVariable id: Int): Route {
        return routeService.getRouteById(id)
    }

    @GetMapping
    fun getFilteredSortedRoutes(
        @RequestParam(required = false) sort: List<String>?,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false) allMap: Map<String, String>
    ): List<Route> {

        val route = allMap.filterKeys { Route.allFields.contains(it) }

        return routeService.filterRoutes(sort, limit, offset, route)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun newRoute(@RequestBody @Validated route: Route): Route {
        return routeService.newRoute(route)
    }

    @PutMapping("/{id}")
    fun updateRoute(@PathVariable id: Int, @RequestBody @Validated route: Route): Route {
        if (route.id != id) throw IllegalArgumentException("Path parameter 'id' is different from entity id")
        return routeService.updateRoute(route)
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: Int) {
        return routeService.deleteRoute(id)
    }
}