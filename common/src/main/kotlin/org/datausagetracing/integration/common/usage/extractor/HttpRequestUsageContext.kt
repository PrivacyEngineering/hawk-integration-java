package org.datausagetracing.integration.common.usage.extractor

interface HttpRequestUsageContext : HttpUsageContext {
    val protocol: String
    val method: String
    val path: String
}