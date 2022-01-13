package org.datausagetracing.integration.common.usage.extractor.http

import org.datausagetracing.integration.common.usage.UsageContext

interface HttpUsageContext : UsageContext {
    val headers: Map<String, List<String>>
    val contentType: String?
    val body: String
    val backed: Any
}