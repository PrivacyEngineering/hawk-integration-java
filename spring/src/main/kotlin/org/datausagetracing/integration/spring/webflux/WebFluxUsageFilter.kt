package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.factory.HostnameBasedServerUsageExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpBodyJacksonUsageExtract
import org.datausagetracing.integration.common.usage.factory.http.HttpHeaderUsageExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpRequestEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpResponseEndpointUsageExtractor
import org.datausagetracing.integration.common.usage.install
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.datausagetracing.integration.common.usage.usageFactory
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
    private val requestUsageFactory = usageFactory {
        install(HostnameBasedServerUsageExtractor())
        install(HttpRequestEndpointUsageExtractor())
        install(HttpHeaderUsageExtractor())
        install(HttpBodyJacksonUsageExtract())
    }
    private val responseUsageFactory = usageFactory {
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

        // Intercept normal HttpServletResponse to cache the bytes of the body
        val cachingResponse = CachingServerHttpResponseDecorator(exchange.response) {
            processResponse(reference, this)
        }

        return chain.filter(exchange.mutate().response(cachingResponse).build())
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

    private fun processResponse(reference: UUID, response: CachingServerHttpResponseDecorator) {
        // Create Usage from response
        val responseUsage = responseUsageFactory.invoke(
            WebfluxHttpResponseUsageContext(
                response,
                "response",
                reference,
                response.content ?: ""
            )
        )

        // Submit generated Usage
        usageProcessor.processUsage(responseUsage)
    }
}