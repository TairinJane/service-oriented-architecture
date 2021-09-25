package filter

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse

@WebFilter(asyncSupported = true, urlPatterns = ["/*"])
class ErrorHandleFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (e: NoSuchElementException) {
            handleError(response, 400, e)
        } catch (e: IllegalArgumentException) {
            handleError(response, 400, e)
        } catch (e: Exception) {
            handleError(response, 500, e)
        }
    }

    override fun destroy() {
    }

    private fun handleError(response: ServletResponse, status: Int, e: Exception) {
        (response as HttpServletResponse).status = status
        response.writer.println(e.message)
        e.printStackTrace()
    }
}