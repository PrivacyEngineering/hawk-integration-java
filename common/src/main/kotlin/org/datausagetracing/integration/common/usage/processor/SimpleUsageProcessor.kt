package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage

class SimpleUsageProcessor(
    usageSampler: UsageSampler,
    usageExporter: UsageExporter
) : AbstractUsageProcessor(usageSampler, usageExporter) {
    override fun doProcessUsage(usage: Usage) {
        if(!usageSampler.shouldSample(usage)) return
        export(listOf(usage))
    }
}