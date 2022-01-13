package org.datausagetracing.integration.common.usage.factory.http

interface HttpResponseUsageContext : HttpUsageContext {
    val status: Int
}