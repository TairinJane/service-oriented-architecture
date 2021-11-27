package com.soa.navigator.controllers

import com.soa.common.model.Route
import com.soa.navigator.service.NavigatorService
import java.util.*
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("routes")
class NavigatorController {

    //    @EJB(lookup = "java:global/ejb-all/NavigatorServiceImpl")
    private val navigatorService: NavigatorService = lookupService()

    @GET
    fun ping(): Response {
        println(navigatorService.ping())
        return Response.ok().entity("Service online").build()
    }

    @GET
    @Path("{from : \\d+}/{to : \\d+}/shortest")
    @Produces(MediaType.APPLICATION_JSON)
    fun getShortestBetween(@PathParam("from") fromId: Int, @PathParam("to") toId: Int): Response {
        val route = navigatorService.findShortestRouteBetween(fromId, toId)
        println("Shortest route: $route")
        if (route != null) return Response.ok().entity(route).type(MediaType.APPLICATION_JSON_TYPE).build()
        return Response.status(404).entity("No routes between locations $fromId and $toId").build()
    }

    @POST
    @Path("add/{from : \\d+}/{to : \\d+}/{distance : ([0-9]*[.])?[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    fun newRouteBetween(
        @PathParam("from") fromId: Int,
        @PathParam("to") toId: Int,
        @PathParam("distance") distance: Float
    ): Response {
        return try {
            val createdRoute = navigatorService.newRouteBetween(fromId, toId, distance)
            println("New Route: $createdRoute")
            Response.status(201).entity(createdRoute).type(MediaType.APPLICATION_JSON_TYPE).build()
        } catch (e: NoSuchElementException) {
            Response.status(404).entity(e.message).build()
        }
    }

    private fun lookupService(): NavigatorService {
        val jndiProperties = Properties()
        jndiProperties[Context.URL_PKG_PREFIXES] = "org.jboss.ejb.client.naming"
        val contextProperties = Properties()
        contextProperties.setProperty(
            Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.enterprise.naming.SerialInitContextFactory"
        )
/*
        val props = Properties()
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory")
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming")
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl")*/

        val context = InitialContext(contextProperties)
        println(context.environment)

        val appName = "global"
        val moduleName = "ejb-all"
        val beanName = "NavigatorServiceImpl"
//        val lookupName = "java:$appName/$moduleName/$beanName"
        val lookupName = "java:global/ejb-all/NavigatorServiceBean!com.soa.ejb.service.NavigatorService"

        return try {
            context.lookup(lookupName) as NavigatorService
        } catch (e: NamingException) {
            println("No bean ($lookupName) found :(")
            e.printStackTrace()
            return navigatorBeanMock
        }
    }
}

val navigatorBeanMock = object : NavigatorService {
    override fun findShortestRouteBetween(fromId: Int, toId: Int): Route? {
        TODO("Not yet implemented")
    }

    override fun newRouteBetween(fromId: Int, toId: Int, distance: Float): Route? {
        TODO("Not yet implemented")
    }

    override fun ping(): String {
        TODO("Not yet implemented")
    }
}