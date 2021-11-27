package com.soa.ejb.client

import com.soa.common.model.Route
import javax.ejb.Remote

@Remote
interface NavigatorClient {
    fun findRoutesByLocations(fromId: Int? = null, toId: Int? = null, limit: Int? = null): List<Route>
    fun newRoute(route: Route): Route?
}