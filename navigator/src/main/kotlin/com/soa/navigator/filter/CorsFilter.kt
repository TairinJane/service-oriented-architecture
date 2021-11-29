package com.soa.navigator.filter

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider


@Provider
class CorsFilter : ContainerResponseFilter {

    override fun filter(
        requestContext: ContainerRequestContext?,
        responseContext: ContainerResponseContext?
    ) {
        responseContext?.headers?.run {
            add("Access-Control-Allow-Origin", "*")
            add("Access-Control-Allow-Credentials", "true")
            add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
        }
    }
}