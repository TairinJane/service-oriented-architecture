package controllers

import model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import services.RouteService

@RestController("/api/routes/min-distance")
class MinDistanceController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping
    fun getRouteWithMinDistance(): Route {
        return routeService.findMinDistanceRoute()
    }
}