package org.datausagetracing.integration.spring.web

import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.factory.extractor.MetadataUsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.http.HttpFieldUsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.http.request.HttpRequestEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.http.request.HttpRequestInitiatorUsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.http.response.HttpResponseEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.jackson.JacksonFieldExtractor
import org.datausagetracing.integration.common.usage.factory.http.JavaEEHttpRequestUsageContext
import org.datausagetracing.integration.common.usage.factory.http.JavaEEHttpResponseUsageContext
import org.datausagetracing.integration.common.usage.install
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.datausagetracing.integration.common.usage.usageFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.LocalDateTime
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
    private val requestUsageFactory = usageFactory {
        install(HttpRequestEndpointUsageExtractor())
        install(HttpRequestInitiatorUsageExtractor())
        install(HttpFieldUsageExtractor(bodyFieldExtractor = JacksonFieldExtractor()))
        install(MetadataUsageExtractor())
    }
    private val responseUsageFactory = usageFactory {
        install(HttpResponseEndpointUsageExtractor())
        install(HttpFieldUsageExtractor())
        install(MetadataUsageExtractor())
    }
    private val logger = LogManager.getLogger(javaClass)

    init {
        println("ff")
    }

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
        try {
            // Create Usage from request
            val usage = requestUsageFactory.invoke(
                JavaEEHttpRequestUsageContext(
                    request,
                    reference,
                    LocalDateTime.now()
                )
            )

            // Submit generated Usage async
            processUsageAsync(usage)
        } catch (throwable: Throwable) {
            logger.error("Can extract process http request", throwable)
        }
    }

    private fun processResponse(reference: UUID, response: ContentCachingResponseWrapper) {
        try {
            // Create Usage from response
            val usage = responseUsageFactory.invoke(
                JavaEEHttpResponseUsageContext(
                    response,
                    reference,
                    String(response.contentAsByteArray),
                    LocalDateTime.now()
                )
            )

            // Submit generated Usage async
            processUsageAsync(usage)
        } catch (throwable: Throwable) {
            logger.error("Can extract process http response", throwable)
        }
    }

    private fun processUsageAsync(usage: Usage) = CompletableFuture.runAsync {
        try {
            usageProcessor.processUsage(usage)
        } catch (throwable: Throwable) {
            logger.catching(throwable)
        }
    }
}