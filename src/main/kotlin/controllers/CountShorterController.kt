package controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import services.RouteService

@RestController("/api/routes/count-shorter-than")
class CountShorterController {

    @Autowired
    private lateinit var routeService: RouteService

    @GetMapping
    fun countShorterThan(@RequestParam distance: Float): Int {
        return routeService.countWithDistanceLessThan(distance)
    }
}