package org.datausagetracing.integration.spring

import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.processor.BatchingUsageProcessor
import org.datausagetracing.integration.common.usage.processor.UsageExporter
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.datausagetracing.integration.common.usage.processor.UsageSampler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.datausagetracing.integration")
open class DataUsageTracingConfiguration {
    private val logger = LogManager.getLogger(javaClass)

    init {
        logger.info("Using Data Usage Tracing")
    }

    @Bean
    fun usageProcessorBean(
        usageSamplers: List<UsageSampler>,
        usageExporters: List<UsageExporter>
    ): UsageProcessor {
        return BatchingUsageProcessor(usageSamplers.first(), usageExporters.first())
    }
}