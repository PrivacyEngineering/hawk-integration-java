package org.datausagetracing.integration.common.usage.extractor.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.extractor.UsageExtractor

open class HttpRequestEndpointUsageExtractor : UsageExtractor<HttpRequestUsageContext> {
    override fun UsageBuilder.extract(context: HttpRequestUsageContext) {
        endpoint {
            protocol = context.protocol
            method = context.method
            path = context.path
        }
    }
}