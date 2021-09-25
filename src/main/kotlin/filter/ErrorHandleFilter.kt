package filter

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

@WebFilter(asyncSupported = true, urlPatterns = ["/*"])
class ErrorHandleFilter: Filter {
    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (e: Exception) {
            println((request as HttpServletRequest).method)
            println(request.requestURL)
            println(e.message)
        }
    }

    override fun destroy() {
    }
}