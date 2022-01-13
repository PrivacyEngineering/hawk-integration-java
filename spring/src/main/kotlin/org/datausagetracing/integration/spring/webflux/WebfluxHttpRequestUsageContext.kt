package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.factory.http.HttpRequestUsageContext
import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.*

class WebfluxHttpRequestUsageContext(
    override val backed: ServerHttpRequest,
    override val phase: String,
    override val reference: UUID
) : HttpRequestUsageContext {
    override val method: String = backed.methodValue
    override val path: String = backed.path.value()
    override val protocol: String = "HTTP/1.1"
    override val headers: Map<String, List<String>> = backed.headers.toMap()
    override val contentType: String? = backed.headers.contentType?.toString()
    override val body: String = ""
}