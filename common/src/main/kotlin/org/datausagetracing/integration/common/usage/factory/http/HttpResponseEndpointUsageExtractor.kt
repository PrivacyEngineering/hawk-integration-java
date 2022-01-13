package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.factory.UsageExtractor

open class HttpResponseEndpointUsageExtractor : UsageExtractor<HttpResponseUsageContext> {
    override fun UsageBuilder.extract(context: HttpResponseUsageContext) {
        endpoint {
            status = context.status.toString()
        }
    }
}