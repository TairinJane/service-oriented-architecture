package com.soa.routes.repository

import com.soa.common.model.Route
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RouteRepository : JpaRepository<Route, Int>, JpaSpecificationExecutor<Route> {

    fun getAllBy(pagination: Pageable): List<Route>

    fun deleteByDistanceEquals(distance: Float): Int

    fun findFirstByOrderByDistanceAsc(): Route

    fun countByDistanceLessThan(distance: Float): Int
}