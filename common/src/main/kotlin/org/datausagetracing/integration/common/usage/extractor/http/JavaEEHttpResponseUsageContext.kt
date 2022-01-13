package org.datausagetracing.integration.common.usage.extractor.http

import java.util.*
import javax.servlet.http.HttpServletResponse

class JavaEEHttpResponseUsageContext(
    override val backed: HttpServletResponse,
    override val phase: String,
    override val reference: UUID,
    override val body: String
) : HttpResponseUsageContext {
    override val status: Int = backed.status
    override val headers: Map<String, List<String>> = backed
        .headerNames
        .associateWith { backed.getHeaders(it).toList() }
    override val contentType: String? = backed.contentType
}