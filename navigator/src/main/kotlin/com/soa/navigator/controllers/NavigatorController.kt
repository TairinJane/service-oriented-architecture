package com.soa.navigator.controllers

import com.soa.common.model.Route
import com.soa.ejb.remote.NavigatorService
import com.soa.navigator.dto.RouteDTO
import com.soa.navigator.dto.toDTO
import com.soa.navigator.exceptions.NavigatorException
import javax.ejb.EJBException
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.naming.InitialContext
import javax.naming.NamingException
import kotlin.jvm.Throws

@WebService
interface NavigatorController {
    @WebMethod
    @WebResult(name = "ping")
    fun ping(): String

    @WebMethod
    @Throws(NavigatorException::class)
    @WebResult(name = "route")
    fun getShortestBetween(@WebParam(name = "fromId") fromId: Int, @WebParam(name = "toId") toId: Int): RouteDTO

    @WebMethod
    @Throws(NavigatorException::class)
    @WebResult(name = "route")
    fun newRouteBetween(
        @WebParam(name = "fromId") fromId: Int,
        @WebParam(name = "toId") toId: Int,
        @WebParam(name = "distance") distance: Float
    ): RouteDTO
}


@WebService(endpointInterface = "com.soa.navigator.controllers.NavigatorController", serviceName = "navigatorService")
class NavigatorControllerImpl : NavigatorController {

    private val navigatorService: NavigatorService = lookupService()

    @WebMethod
    @WebResult(name = "ping")
    override fun ping(): String {
        return navigatorService.ping()
    }

    @WebMethod
    @WebResult(name = "route")
    override fun getShortestBetween(fromId: Int, toId: Int): RouteDTO {
        val route = try {
            navigatorService.findShortestRouteBetween(fromId, toId)
        } catch (e: Exception) {
            println(e.message)
            throw NavigatorException(e.message)
        }
        println("Shortest route: $route")
        return route?.toDTO() ?: throw NavigatorException("No routes between locations $fromId and $toId")
    }

    @WebMethod
    @WebResult(name = "route")
    override fun newRouteBetween(fromId: Int, toId: Int, distance: Float): RouteDTO {
        return try {
            val createdRoute = navigatorService.newRouteBetween(fromId, toId, distance)
            println("New Route: $createdRoute")
            createdRoute.toDTO()
        } catch (e: NoSuchElementException) {
            throw NavigatorException(e.message, 404)
        } catch (e: EJBException) {
            if (e.causedByException is NoSuchElementException)
                throw NavigatorException(e.message, 404)
            else
                throw NavigatorException(e.message)
        }
    }

    private fun lookupService(): NavigatorService {
        val context = InitialContext()
        val lookupName = navigatorLookupName()

        return try {
            context.lookup(lookupName) as NavigatorService
        } catch (e: NamingException) {
            println("No bean ($lookupName) found :(")
            return navigatorBeanMock
        }
    }

    private fun navigatorLookupName(): String {
        val appName = System.getProperty("ejb.app.name")
        val moduleName = System.getProperty("ejb.module.name")
        val beanName = System.getProperty("ejb.bean.name")
        return "java:$appName/$moduleName/$beanName"
    }
}

val navigatorBeanMock = object : NavigatorService {
    override fun findShortestRouteBetween(fromId: Int, toId: Int): Route? {
        TODO("Not yet implemented")
    }

    override fun newRouteBetween(fromId: Int, toId: Int, distance: Float): Route {
        TODO("Not yet implemented")
    }

    override fun ping(): String {
        TODO("Not yet implemented")
    }
}