package com.soa.navigator.exceptions

import javax.ws.rs.ProcessingException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ProcessingExceptionMapper : ExceptionMapper<ProcessingException> {
    override fun toResponse(e: ProcessingException): Response {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Error occurred when processing request. Try later")
            .build()
    }
}