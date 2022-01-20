package org.datausagetracing.integration.common.usage.factory.extractor.http.request

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.factory.Side
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.HostExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.HostnameLocalHostExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpRequestUsageContext
import org.datausagetracing.integration.common.usage.initiator

open class HttpRequestInitiatorUsageExtractor(
    protected val localHostExtractor: HostExtractor<in HttpRequestUsageContext> = HostnameLocalHostExtractor(),
    protected val remoteHostExtractor: HostExtractor<in HttpRequestUsageContext> = HttpRemoteHostExtractor()
) : UsageExtractor<HttpRequestUsageContext> {

    override fun UsageBuilder.extract(context: HttpRequestUsageContext) {
        initiator {
            host = when(context.side) {
                Side.SERVER -> remoteHostExtractor.extractHost(context)
                Side.CLIENT -> localHostExtractor.extractHost(context)
            }
        }
    }
}