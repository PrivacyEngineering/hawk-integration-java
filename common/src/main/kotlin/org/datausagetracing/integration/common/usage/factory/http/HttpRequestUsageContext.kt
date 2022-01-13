package org.datausagetracing.integration.common.usage.factory.http

interface HttpRequestUsageContext : HttpUsageContext {
    val protocol: String
    val method: String
    val path: String
}