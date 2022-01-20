package org.datausagetracing.integration.common.usage.factory.extractor.http.request

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.factory.Side
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.HostExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.HostnameLocalHostExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpRequestUsageContext

open class HttpRequestEndpointUsageExtractor(
    protected val localHostExtractor: HostExtractor<in HttpRequestUsageContext> = HostnameLocalHostExtractor(),
    protected val remoteHostExtractor: HostExtractor<in HttpRequestUsageContext> = HttpRemoteHostExtractor()
) : UsageExtractor<HttpRequestUsageContext> {

    override fun UsageBuilder.extract(context: HttpRequestUsageContext) {
        endpoint {
            host = when(context.side) {
                Side.SERVER -> localHostExtractor.extractHost(context)
                Side.CLIENT -> remoteHostExtractor.extractHost(context)
            }
            protocol = context.protocol
            method = context.method
            path = context.path
            id = "http:$method:$host:$path"
        }
    }
}