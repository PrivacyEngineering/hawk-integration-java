package org.datausagetracing.integration.spring

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.UsageFactory
import org.datausagetracing.integration.common.usage.extractor.*
import org.datausagetracing.integration.common.usage.install
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebTracingFilter : Filter {
    private val logger = LogManager.getLogger(javaClass)

    private val requestUsageFactory = UsageFactory().apply {
        install(HostnameBasedServerUsageExtractor())
        install(HttpRequestEndpointUsageExtractor())
        install(HttpHeaderUsageExtract())
        install(HttpBodyJacksonUsageExtract())
    }
    private val responseUsageFactory = UsageFactory().apply {
        install(HttpHeaderUsageExtract())
        install(HttpBodyJacksonUsageExtract())
        install(HttpResponseEndpointUsageExtractor())
    }

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {

        // Generate random reference id
        val reference = UUID.randomUUID()

        // Create Usage from request
        val requestUsage = requestUsageFactory.create(
            JavaEEHttpRequestUsageContext(
                request as HttpServletRequest,
                "request",
                reference
            )
        )
        logger.info("Request: ${ObjectMapper().writeValueAsString(requestUsage)}")

        // Intercept normal HttpServletResponse to cache the bytes of the body
        val cachingResponse = ContentCachingResponseWrapper(response as HttpServletResponse)

        // Continue Application
        chain.doFilter(request, cachingResponse)

        // Create Usage from response
        val responseUsage = responseUsageFactory.create(
            JavaEEHttpResponseUsageContext(
                cachingResponse,
                "response",
                reference,
                String(cachingResponse.contentAsByteArray)
            )
        )
        logger.info("Response: ${ObjectMapper().writeValueAsString(responseUsage)}")
    }
}