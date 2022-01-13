package org.datausagetracing.integration.common.usage.factory

import org.datausagetracing.integration.common.usage.UsageBuilder

interface UsageExtractor<C : UsageContext> {
    fun UsageBuilder.extract(context: C)
}