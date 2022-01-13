package org.datausagetracing.integration.common.usage.extractor.http

interface HttpRequestUsageContext : HttpUsageContext {
    val protocol: String
    val method: String
    val path: String
}