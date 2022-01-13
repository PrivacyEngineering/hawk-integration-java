package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.UsageFactory
import org.datausagetracing.integration.common.usage.extractor.HostnameBasedServerUsageExtractor
import org.datausagetracing.integration.common.usage.extractor.http.HttpBodyJacksonUsageExtract
import org.datausagetracing.integration.common.usage.extractor.http.HttpHeaderUsageExtractor
import org.datausagetracing.integration.common.usage.extractor.http.HttpRequestEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.extractor.http.HttpResponseEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.install
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.DispatcherHandler
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Service
@ConditionalOnClass(DispatcherHandler::class)
class WebFluxUsageFilter(private val usageProcessor: UsageProcessor) : WebFilter {
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

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // Generate random reference id
        val reference = UUID.randomUUID()

        // Process request
        // We can assume, that the request is always a HttpServletRequest
        processRequest(reference, exchange.request)

        // TODO

//        // Intercept normal HttpServletResponse to cache the bytes of the body
//        val cachingResponse = ContentCachingResponseWrapper(response as HttpServletResponse)
//
//        // Continue Application
//        chain.doFilter(request, cachingResponse)
//
//        // Process response
//        processResponse(reference, cachingResponse)

        return chain.filter(exchange)
    }

    private fun processRequest(reference: UUID, request: ServerHttpRequest) {
        // Create Usage from request
        val requestUsage = requestUsageFactory.invoke(
            WebfluxHttpRequestUsageContext(
                request,
                "request",
                reference
            )
        )

        // Submit generated Usage
        usageProcessor.processUsage(requestUsage)
    }

//    private fun processResponse(reference: UUID, response: ContentCachingResponseWrapper) {
//        // Create Usage from response
//        val responseUsage = responseUsageFactory.invoke(
//            JavaEEHttpResponseUsageContext(
//                response,
//                "response",
//                reference,
//                String(response.contentAsByteArray)
//            )
//        )
//
//        // Submit generated Usage
//        usageProcessor.processUsage(responseUsage)
//    }
}