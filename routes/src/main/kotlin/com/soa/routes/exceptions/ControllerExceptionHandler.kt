package com.example.exceptions

import com.soa.routes.exceptions.RouteNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException


@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException, headers: HttpHeaders?,
        status: HttpStatus?, request: WebRequest
    ): ResponseEntity<Any> {
        val validationErrors = exception.bindingResult
            .fieldErrors
            .map { error -> error.field + ": " + error.defaultMessage }
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, validationErrors)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RouteNotFoundException::class)
    fun handleRouteNotFound(exception: RouteNotFoundException): ResponseEntity<Any> {
        return getExceptionResponseEntity(HttpStatus.NOT_FOUND, listOf(exception.message ?: "Route not found"))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(exception: IllegalArgumentException): ResponseEntity<Any> {
        exception.printStackTrace()
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, listOf(exception.message ?: "Argument is not valid"))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        exception: ConstraintViolationException, request: WebRequest
    ): ResponseEntity<Any> {
        val validationErrors = exception.constraintViolations.map { violation ->
            violation.propertyPath.toString() + ": " + violation.message
        }
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, validationErrors)
    }

    private fun getExceptionResponseEntity(
        status: HttpStatus,
        errors: List<String>
    ): ResponseEntity<Any> {
        val errorsMessage =
            if (errors.isNotEmpty()) errors.filter { it.isNotEmpty() }.joinToString() else status.reasonPhrase

        return ResponseEntity(errorsMessage, status)
    }
}
