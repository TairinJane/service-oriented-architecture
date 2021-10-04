package filter

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(asyncSupported = true, urlPatterns = ["/*"])
class CORSFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) {
        val request = servletRequest as HttpServletRequest

        (servletResponse as HttpServletResponse).addHeader("Access-Control-Allow-Origin", "*")
        servletResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST")

        if (request.method == "OPTIONS") {
            servletResponse.status = HttpServletResponse.SC_ACCEPTED
            return
        }

        chain.doFilter(request, servletResponse)
    }

    override fun destroy() {
    }
}
