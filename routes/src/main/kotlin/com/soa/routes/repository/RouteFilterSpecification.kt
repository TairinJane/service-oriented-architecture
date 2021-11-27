package com.soa.routes.repository

import com.soa.common.model.Coordinates
import com.soa.common.model.LocationFrom
import com.soa.common.model.LocationTo
import com.soa.common.model.Route
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class RouteFilterSpecification(private val filter: Map<String, Any>) : Specification<Route> {

    override fun toPredicate(root: Root<Route>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {

        if (filter.isEmpty()) return null

        val coordinatesJoin = root.join<Route, Coordinates>("coordinates")
        val fromJoin = root.join<Route, LocationFrom>("from")
        val toJoin = root.join<Route, LocationTo>("to")

        val predicates = filter.entries.map { (fieldName, value) ->
            val (table, field) = if ('.' in fieldName) fieldName.split('.') else listOf("", fieldName)

            val tableRoot = when (table) {
                "coordinates" -> coordinatesJoin
                "from" -> fromJoin
                "to" -> toJoin
                else -> root
            }
            criteriaBuilder.equal(tableRoot.get<String>(field), value)
        }

        return criteriaBuilder.and(*predicates.toTypedArray())
    }
}