package org.datausagetracing.integration.common.usage.factory.extractor.http.request

import org.datausagetracing.integration.common.usage.factory.extractor.HostExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpRequestUsageContext

class HttpRemoteHostExtractor: HostExtractor<HttpRequestUsageContext> {
    override fun extractHost(context: HttpRequestUsageContext) = context.remoteHost
}