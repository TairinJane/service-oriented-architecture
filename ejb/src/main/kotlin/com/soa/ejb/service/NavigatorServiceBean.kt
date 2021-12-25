package com.soa.ejb.service

import com.soa.common.model.Coordinates
import com.soa.common.model.Route
import com.soa.ejb.remote.NavigatorService
import org.glassfish.jersey.SslConfigurator
import javax.ejb.Stateless
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@Stateless
class NavigatorServiceBean : NavigatorService {

    private val ROUTES_URL = getRoutesUrl()

    private val sslContext = SslConfigurator.newInstance()
        .trustStorePassword("payara")
        .keyPassword("payara")
        .createSSLContext()

    private val client: Client =
        ClientBuilder.newBuilder()
            .sslContext(sslContext)
            .hostnameVerifier { hostname, _ -> hostname == "localhost" }
            .build()

    private val target = client.target(ROUTES_URL)

    override fun findShortestRouteBetween(fromId: Int, toId: Int): Route? {
        val routes = findRoutesByLocations(fromId = fromId, toId = toId, limit = null)
        return routes.minByOrNull { it.distance ?: Float.MAX_VALUE }
    }

    override fun newRouteBetween(fromId: Int, toId: Int, distance: Float): Route {
        val fromRoute = findRoutesByLocations(fromId = fromId, limit = 1, toId = null)
        if (fromRoute.isEmpty()) throw NoSuchElementException("Location (from) with id = $fromId doesn't exist")

        val toRoute = findRoutesByLocations(toId = toId, limit = 1, fromId = null)
        if (toRoute.isEmpty()) throw NoSuchElementException("Location (to) with id = $toId doesn't exist")

        val from = fromRoute[0].from
        val to = toRoute[0].to

        val newRoute = Route(
            name = "Route from $fromId to $toId",
            coordinates = Coordinates(x = fromId.toFloat(), y = toId.toFloat(), id = null),
            from = from,
            to = to,
            distance = distance,
            creationDate = null,
            id = null
        )

        return newRoute(newRoute)

    }

    override fun ping(): String {
        return "ping ${this::class.qualifiedName}"
    }

    private fun findRoutesByLocations(fromId: Int?, toId: Int?, limit: Int?): List<Route> {
        var target = target

        if (fromId != null) target = target.queryParam("from.id", fromId)
        if (toId != null) target = target.queryParam("to.id", toId)
        if (limit != null) target = target.queryParam("limit", limit)

        return target
            .request(MediaType.APPLICATION_JSON)
            .get(object : GenericType<List<Route>>() {})
    }

    private fun newRoute(route: Route): Route {
        return target
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(route, MediaType.APPLICATION_JSON))
            .readEntity(Route::class.java)
    }

    private fun getRoutesUrl(): String = System.getProperty("routes.url")
}