package com.example.repository

import com.example.model.Route
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RouteRepository : JpaRepository<Route, Int>, JpaSpecificationExecutor<Route> {

    fun getAllBy(pagination: Pageable): List<Route>

    fun deleteByDistanceEquals(distance: Float): Int

    @Query(value = "select r from Route r WHERE r.distance = (select min(r.distance) from Route r)")
    fun findWithMinDistance(): Route

    fun countByDistanceLessThan(distance: Float): Int
}