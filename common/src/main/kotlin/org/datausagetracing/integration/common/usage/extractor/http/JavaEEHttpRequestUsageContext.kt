package org.datausagetracing.integration.common.usage.extractor.http

import java.util.*
import javax.servlet.http.HttpServletRequest

class JavaEEHttpRequestUsageContext(
    override val backed: HttpServletRequest,
    override val phase: String,
    override val reference: UUID
) : HttpRequestUsageContext {
    override val method: String = backed.method
    override val path: String = backed.requestURI
    override val protocol: String = backed.protocol
    override val headers: Map<String, List<String>> = backed.headerNames
        .asSequence()
        .associateWith { backed.getHeaders(it).toList() }
    override val contentType: String? = backed.contentType
    override val body: String = backed.reader.readText()
}
