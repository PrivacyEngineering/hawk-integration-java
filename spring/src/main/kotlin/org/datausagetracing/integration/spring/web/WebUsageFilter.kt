package org.datausagetracing.integration.spring.web

import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.UsageFactory
import org.datausagetracing.integration.common.usage.extractor.HostnameBasedServerUsageExtractor
import org.datausagetracing.integration.common.usage.extractor.http.*
import org.datausagetracing.integration.common.usage.install
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@ConditionalOnClass(DispatcherServlet::class)
class WebUsageFilter(private val usageProcessor: UsageProcessor) : Filter {
    private val requestUsageFactory = UsageFactory().apply {
        install(HostnameBasedServerUsageExtractor())
        install(HttpRequestEndpointUsageExtractor())
        install(HttpHeaderUsageExtractor())
        install(HttpBodyJacksonUsageExtract())
    }
    private val responseUsageFactory = UsageFactory().apply {
        install(HttpHeaderUsageExtractor())
        install(HttpBodyJacksonUsageExtract())
        install(HttpResponseEndpointUsageExtractor())
    }
    private val logger = LogManager.getLogger(javaClass)

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        // Generate random reference id
        val reference = UUID.randomUUID()

        // Process request
        // We can assume, that the request is always a HttpServletRequest
        processRequest(reference, request as HttpServletRequest)

        // Intercept normal HttpServletResponse to cache the bytes of the body
        val cachingResponse = ContentCachingResponseWrapper(response as HttpServletResponse)

        // Continue Application
        chain.doFilter(request, cachingResponse)

        // Process response
        processResponse(reference, cachingResponse)

        // Send body to client
        cachingResponse.copyBodyToResponse()
    }

    private fun processRequest(reference: UUID, request: HttpServletRequest) {
        // Create Usage from request
        val usage = requestUsageFactory.invoke(
            JavaEEHttpRequestUsageContext(
                request,
                "request",
                reference
            )
        )

        // Submit generated Usage async
        processUsageAsync(usage)
    }

    private fun processResponse(reference: UUID, response: ContentCachingResponseWrapper) {
        // Create Usage from response
        val usage = responseUsageFactory.invoke(
            JavaEEHttpResponseUsageContext(
                response,
                "response",
                reference,
                String(response.contentAsByteArray)
            )
        )

        // Submit generated Usage async
        processUsageAsync(usage)
    }

    private fun processUsageAsync(usage: Usage) = CompletableFuture.runAsync {
        try {
            usageProcessor.processUsage(usage)
        } catch (throwable: Throwable) {
            logger.catching(throwable)
        }
    }
}