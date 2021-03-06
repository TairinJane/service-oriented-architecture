package com.soa.routes.controllers

import com.soa.routes.services.RouteService
import com.soa.common.model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/routes/min-distance")
class MinDistanceController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping(produces = ["application/json"])
    fun getRouteWithMinDistance(): Route {
        return routeService.findMinDistanceRoute()
    }
}