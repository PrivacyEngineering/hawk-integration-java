package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.factory.Side
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletResponse

class JavaEEHttpResponseUsageContext(
    override val backed: HttpServletResponse,
    override val id: UUID,
    override val body: String,
    override val timestamp: LocalDateTime
) : HttpResponseUsageContext {
    override val side: Side = Side.SERVER
    override val status: Int = backed.status
    override val headers: Map<String, List<String>> = backed
        .headerNames
        .associateWith { backed.getHeaders(it).toList() }
    override val contentType: String? = backed.contentType
}