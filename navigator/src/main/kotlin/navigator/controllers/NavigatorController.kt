package navigator.controllers

import navigator.service.NavigatorService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("routes")
class NavigatorController {

    private val service = NavigatorService.instance

    @GET
    fun ping(): Response {
        return Response.ok().entity("Service online").build()
    }

    @GET
    @Path("{from}/{to}/shortest")
    @Produces(MediaType.APPLICATION_JSON)
    fun getShortestBetween(@PathParam("from") fromId: Int, @PathParam("to") toId: Int): Response {
        val route = service.findShortestRouteBetween(fromId, toId)
        println("Shortest route: $route")
        if (route != null) return Response.ok().entity(route).type(MediaType.APPLICATION_JSON_TYPE).build()
        return Response.status(404).entity("No routes between locations $fromId and $toId").build()
    }

    @POST
    @Path("add/{from}/{to}/{distance}")
    @Produces(MediaType.APPLICATION_JSON)
    fun newRouteBetween(
        @PathParam("from") fromId: Int,
        @PathParam("to") toId: Int,
        @PathParam("distance") distance: Float
    ): Response {
        return try {
            val createdRoute = service.newRouteBetween(fromId, toId, distance)
            println("New Route: $createdRoute")
            Response.status(201).entity(createdRoute).type(MediaType.APPLICATION_JSON_TYPE).build()
        } catch (e: NoSuchElementException) {
            Response.status(404).entity(e.message).build()
        }
    }
}