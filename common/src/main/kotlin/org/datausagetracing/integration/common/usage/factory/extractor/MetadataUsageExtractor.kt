package org.datausagetracing.integration.common.usage.factory.extractor

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.factory.UsageContext
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.metadata
import java.time.ZonedDateTime

class MetadataUsageExtractor: UsageExtractor<UsageContext> {
    override fun UsageBuilder.extract(context: UsageContext) {
        metadata {
            side = context.side.name
            phase = context.phase.name
            timestamp(ZonedDateTime.now())
        }
    }
}