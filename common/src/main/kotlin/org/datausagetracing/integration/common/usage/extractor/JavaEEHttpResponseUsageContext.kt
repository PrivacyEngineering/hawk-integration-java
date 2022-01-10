package org.datausagetracing.integration.common.usage.extractor

import java.util.*
import javax.servlet.http.HttpServletResponse

class JavaEEHttpResponseUsageContext(
    override val backed: HttpServletResponse,
    override val phase: String,
    override val reference: UUID,
    override val body: String
) : HttpResponseUsageContext {
    override val status: Int = backed.status
    override val headers: Map<String, List<String>>
        get() = backed
            .headerNames
            .associateWith { backed.getHeaders(it).toList() }
}