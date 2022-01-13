package org.datausagetracing.integration.common.usage.extractor.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.extractor.UsageExtractor

open class HttpResponseEndpointUsageExtractor : UsageExtractor<HttpResponseUsageContext> {
    override fun UsageBuilder.extract(context: HttpResponseUsageContext) {
        endpoint {
            status = context.status.toString()
        }
    }
}