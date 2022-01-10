package org.datausagetracing.integration.common.usage.extractor

interface HttpResponseUsageContext : HttpUsageContext {
    val status: Int
}