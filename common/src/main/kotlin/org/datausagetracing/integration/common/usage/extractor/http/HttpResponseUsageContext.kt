package org.datausagetracing.integration.common.usage.extractor.http

interface HttpResponseUsageContext : HttpUsageContext {
    val status: Int
}