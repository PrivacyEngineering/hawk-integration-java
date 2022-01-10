package org.datausagetracing.integration.common.usage.extractor

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint

open class HttpResponseEndpointUsageExtractor : UsageExtractor<HttpResponseUsageContext> {
    override fun UsageBuilder.extract(context: HttpResponseUsageContext) {
        endpoint {
            status = context.status.toString()
        }
    }
}