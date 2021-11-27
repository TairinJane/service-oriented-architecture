package com.soa.ejb.client

import com.soa.common.model.Route
import org.glassfish.jersey.SslConfigurator
import javax.ejb.Stateless
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@Stateless
class NavigatorClientBean : NavigatorClient {
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

    override fun findRoutesByLocations(fromId: Int?, toId: Int?, limit: Int?): List<Route> {
        var target = target

        if (fromId != null) target = target.queryParam("from.id", fromId)
        if (toId != null) target = target.queryParam("to.id", toId)
        if (limit != null) target = target.queryParam("limit", limit)

        return target
            .request(MediaType.APPLICATION_JSON)
            .get(object : GenericType<List<Route>>() {})
    }

    override fun newRoute(route: Route): Route? {
        return target
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(route, MediaType.APPLICATION_JSON))
            .readEntity(Route::class.java)
    }

    private fun getRoutesUrl(): String = System.getProperty("routes.url")
}