package com.soa.routes.services

import com.soa.routes.exceptions.RouteNotFoundException
import com.soa.routes.util.LimitOffsetPagination
import com.soa.common.model.Route
import com.soa.routes.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

interface RouteService {
    fun getRouteById(routeId: Int): Route
    fun newRoute(@Validated route: Route): Route
    fun updateRoute(@Validated route: Route): Route
    fun deleteRoute(routeId: Int)
    fun filterRoutes(sortList: List<String>?, limit: Int?, offset: Int, routeFilters: Map<String, String>): List<Route>
    fun deleteWithDistanceEquals(distance: Float): Int
    fun findMinDistanceRoute(): Route
    fun countWithDistanceLessThan(distance: Float): Int
}

@Service
class RouteServiceImpl : RouteService {

    @Autowired
    private lateinit var routeRepository: RouteRepository

    @Autowired
    private lateinit var coordinatesRepository: CoordinatesRepository

    @Autowired
    private lateinit var fromRepository: LocationFromRepository

    @Autowired
    private lateinit var toRepository: LocationToRepository

    override fun getRouteById(routeId: Int): Route {
        return routeRepository.findById(routeId).orElseThrow { RouteNotFoundException(routeId) }
    }

    override fun newRoute(route: Route): Route {
        route.apply {
            val coordinatesId = coordinates?.id
            if (coordinatesId != null) coordinates = coordinatesRepository.findById(coordinatesId).get()

            val fromId = from?.id
            if (fromId != null) from = fromRepository.findById(fromId).get()

            val toId = to?.id
            if (toId != null) to = toRepository.findById(toId).get()
        }
        route.checkConstraints()
        return routeRepository.save(route)
    }

    override fun updateRoute(route: Route): Route {
        route.checkConstraints()
        return routeRepository.save(route)
    }

    override fun deleteRoute(routeId: Int) {
        if (!routeRepository.existsById(routeId)) throw RouteNotFoundException(routeId)
        return routeRepository.deleteById(routeId)
    }

    override fun filterRoutes(
        sortList: List<String>?,
        limit: Int?,
        offset: Int,
        routeFilters: Map<String, String>
    ): List<Route> {
        val sorting = parseSorting(sortList)
        val routeSpec = RouteFilterSpecification(routeFilters)

        if (limit == null) return routeRepository.findAll(routeSpec, sorting)

        val pagination = LimitOffsetPagination(offset, limit, sorting)
        return routeRepository.findAll(routeSpec, pagination).toList()
    }

    override fun deleteWithDistanceEquals(distance: Float): Int {
        return routeRepository.deleteByDistanceEquals(distance)
    }

    override fun findMinDistanceRoute(): Route {
        return routeRepository.findFirstByOrderByDistanceAsc()
    }

    override fun countWithDistanceLessThan(distance: Float): Int {
        return routeRepository.countByDistanceLessThan(distance)
    }

    private fun parseSorting(sortingList: List<String>?): Sort {
        println(sortingList)

        if (sortingList.isNullOrEmpty()) return Sort.unsorted()

        var sorting = Sort.unsorted()
        sortingList.forEach {
            val sortDirection = if (it[0] == '-') Sort.Direction.DESC else Sort.Direction.ASC
            val field = it.removePrefix("-")

            if (field !in Route.allFields) throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

            val sort = Sort.by(sortDirection, field)

            sorting = sorting.and(sort)
        }
        return sorting
    }
}