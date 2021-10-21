package com.example.controllers

import com.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/routes/delete-with-distance")
class DeleteController {

    @Autowired
    private lateinit var routeService: RouteService

    @DeleteMapping
    fun deleteByDistance(@RequestParam distance: Float): Int {
        return routeService.deleteWithDistanceEquals(distance)
    }
}