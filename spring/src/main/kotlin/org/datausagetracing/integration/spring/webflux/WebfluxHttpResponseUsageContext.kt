package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.factory.http.HttpResponseUsageContext
import org.springframework.http.server.reactive.ServerHttpResponse
import java.util.*

class WebfluxHttpResponseUsageContext(
    override val backed: ServerHttpResponse,
    override val phase: String,
    override val reference: UUID,
    override val body: String,
) : HttpResponseUsageContext {
    override val status: Int = backed.rawStatusCode ?: 200
    override val headers: Map<String, List<String>> = backed.headers.toMap()
    override val contentType: String? = backed.headers.contentType?.toString()
}