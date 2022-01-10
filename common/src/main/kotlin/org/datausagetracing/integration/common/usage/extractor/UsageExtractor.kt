package org.datausagetracing.integration.common.usage.extractor

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.UsageContext

interface UsageExtractor<C : UsageContext> {
    fun UsageBuilder.extract(context: C)
}