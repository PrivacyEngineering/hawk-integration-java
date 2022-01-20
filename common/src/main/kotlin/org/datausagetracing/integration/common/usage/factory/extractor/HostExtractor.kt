package org.datausagetracing.integration.common.usage.factory.extractor

import org.datausagetracing.integration.common.usage.factory.UsageContext

interface HostExtractor<C : UsageContext> {
    fun extractHost(context: C): String
}