package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.factory.Side
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

class JavaEEHttpRequestUsageContext(
    override val backed: HttpServletRequest,
    override val id: UUID,
    override val timestamp: LocalDateTime
) : HttpRequestUsageContext {
    override val side: Side = Side.SERVER
    override val method: String = backed.method
    override val path: String = backed.requestURI
    override val remoteHost: String get() = backed.remoteHost
    override val protocol: String = backed.protocol
    override val headers: Map<String, List<String>> = backed.headerNames
        .asSequence()
        .associateWith { backed.getHeaders(it).toList() }
    override val contentType: String? = backed.contentType
    override val body: String = backed.reader.readText()
}
