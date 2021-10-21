package com.example.services

import com.example.exceptions.RouteNotFoundException
import com.example.model.Route
import com.example.repository.RouteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import util.LimitOffsetPagination
import util.SortType

interface RouteService {
    fun getRouteById(routeId: Int): Route
    fun newRoute(@Validated route: Route): Route
    fun updateRoute(@Validated route: Route): Route
    fun deleteRoute(routeId: Int)
    fun filterRoutes(sortList: List<String>?, limit: Int, offset: Int, filterRoute: Route): List<Route>
    fun deleteWithDistanceEquals(distance: Float): Int
    fun findMinDistanceRoute(): Route
    fun countWithDistanceLessThan(distance: Float): Int
}

@Service
class RouteServiceImpl : RouteService {

    @Autowired
    private lateinit var routeRepository: RouteRepository

    override fun getRouteById(routeId: Int): Route {
        return routeRepository.findById(routeId).orElseThrow { RouteNotFoundException(routeId) }
    }

    override fun newRoute(@Validated route: Route): Route {
        return routeRepository.save(route)
    }

    override fun updateRoute(@Validated route: Route): Route {
        return routeRepository.save(route)
    }

    override fun deleteRoute(routeId: Int) {
        if (!routeRepository.existsById(routeId)) throw RouteNotFoundException(routeId)
        return routeRepository.deleteById(routeId)
    }

    override fun filterRoutes(sortList: List<String>?, limit: Int, offset: Int, filterRoute: Route): List<Route> {
        val routeExample = Example.of(filterRoute)
        val sorting = parseSorting(sortList)
        val pagination = LimitOffsetPagination(offset, limit, sorting)

        return routeRepository.findAllBy(routeExample, pagination)
    }

    override fun deleteWithDistanceEquals(distance: Float): Int {
        return routeRepository.deleteByDistanceEquals(distance)
    }

    override fun findMinDistanceRoute(): Route {
        return routeRepository.findWithMinDistance()
    }

    override fun countWithDistanceLessThan(distance: Float): Int {
        return routeRepository.countByDistanceLessThan(distance)
    }

    private fun parseSorting(sortingList: List<String>?): Sort {
        val sorting = Sort.unsorted()
        sortingList?.forEach {
            val sortType = if (it[0] == '-') SortType.DESC else SortType.ASC
            val field = it.removePrefix("-")

            if (field !in Route.allFields) throw IllegalArgumentException("Sorting parameter '$field' is not allowed")

            val sort = Sort.by(field)
            if (sortType == SortType.DESC) sort.descending()

            sorting.and(sort)
        }
        return sorting
    }
}