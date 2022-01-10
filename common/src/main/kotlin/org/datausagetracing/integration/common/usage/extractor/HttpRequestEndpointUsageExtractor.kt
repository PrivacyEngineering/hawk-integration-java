package org.datausagetracing.integration.common.usage.extractor

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint

open class HttpRequestEndpointUsageExtractor : UsageExtractor<HttpRequestUsageContext> {
    override fun UsageBuilder.extract(context: HttpRequestUsageContext) {
        endpoint {
            protocol = context.protocol
            method = context.method
            path = context.path
        }
    }
}