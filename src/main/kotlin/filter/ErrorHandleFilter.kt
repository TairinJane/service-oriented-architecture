package filter

import com.google.gson.JsonParseException
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse
import javax.validation.ValidationException

//@WebFilter(asyncSupported = true, urlPatterns = ["/*"])
class ErrorHandleFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (e: NoSuchElementException) {
            handleError(response, 404, e)
        } catch (e: IllegalArgumentException) {
            handleError(response, 400, e)
        } catch (e: ValidationException) {
            handleError(response, 400, e)
        } catch (e: JsonParseException) {
            handleError(response, 400, e)
        } catch (e: Exception) {
            handleError(response, 500, ServletException("Server error"))
        }
    }

    override fun destroy() {
    }

    private fun handleError(response: ServletResponse, status: Int, e: Exception) {
        (response as HttpServletResponse).status = status
        response.writer.println(e.message)
        e.cause?.message?.let { response.writer.println(it) }
        e.printStackTrace()
    }
}