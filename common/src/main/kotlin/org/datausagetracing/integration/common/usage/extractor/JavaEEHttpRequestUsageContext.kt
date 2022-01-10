package org.datausagetracing.integration.common.usage.extractor

import java.util.*
import javax.servlet.http.HttpServletRequest

class JavaEEHttpRequestUsageContext(
    override val backed: HttpServletRequest,
    override val phase: String,
    override val reference: UUID
) : HttpRequestUsageContext {
    override val method: String get() = backed.method
    override val path: String get() = backed.requestURI
    override val protocol: String get() = backed.protocol
    override val headers: Map<String, List<String>> get() = backed.headerNames
        .asSequence()
        .associateWith { backed.getHeaders(it).toList() }
    override val body: String get() = backed.reader.readText()
}
