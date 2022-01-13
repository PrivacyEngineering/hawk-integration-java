package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.factory.UsageContext

interface HttpUsageContext : UsageContext {
    val headers: Map<String, List<String>>
    val contentType: String?
    val body: String
    val backed: Any
}