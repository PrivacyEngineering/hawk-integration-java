package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractUsageProcessor(
    override val usageSampler: UsageSampler,
    override val usageExporter: UsageExporter
) : UsageProcessor {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun processUsage(usage: Usage) {
        if(!usageSampler.shouldSample(usage)) {
            logger.trace("Skip {}, because it's not sampled")
            return
        } else {
            logger.trace("Process {}", usage)
            doProcessUsage(usage)
        }
    }

    protected abstract fun doProcessUsage(usage: Usage)

    protected open fun export(usages: List<Usage>) {
        if(usages.isEmpty()) return

        try {
            logger.trace("Exporting usages {}", usages)
            usageExporter.export(usages)
        } catch (throwable: Throwable) {
            logger.error("Could export ${usages.size} usages", throwable)
            logger.debug("Could not export usages {}", usages)
        }
    }
}