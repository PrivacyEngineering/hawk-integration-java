package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.extractor.http.HttpRequestUsageContext
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.server.reactive.ServerHttpRequest
import java.nio.charset.StandardCharsets
import java.time.Duration
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
    override val contentType: String
        get() = TODO("Not yet implemented")
    override val body: String = backed.body.map {
        val content = StandardCharsets.UTF_8.decode(it.asByteBuffer()).toString()
        DataBufferUtils.release(it)
        content
    }.collectList().block(Duration.ofSeconds(2L))?.joinToString("") ?: ""
}