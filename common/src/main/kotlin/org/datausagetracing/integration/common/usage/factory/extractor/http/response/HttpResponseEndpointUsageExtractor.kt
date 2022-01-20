package org.datausagetracing.integration.common.usage.factory.extractor.http.response

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpResponseUsageContext

open class HttpResponseEndpointUsageExtractor : UsageExtractor<HttpResponseUsageContext> {
    override fun UsageBuilder.extract(context: HttpResponseUsageContext) {
        endpoint {
            status = context.status.toString()
        }
    }
}