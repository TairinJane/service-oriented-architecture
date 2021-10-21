package com.example.controllers

import com.example.model.Route
import com.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/routes/min-distance")
class MinDistanceController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping
    fun getRouteWithMinDistance(): Route {
        return routeService.findMinDistanceRoute()
    }
}