package org.datausagetracing.integration.spring

import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.processor.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.datausagetracing.integration")
class HawkConfiguration {
    private val logger = LogManager.getLogger(javaClass)
    @Value("\${hawk.usage.batch:true}")
    private var shouldBatch: Boolean = true

    init {
        logger.info("Using Hawk Integration")
    }

    @Bean
    fun usageProcessorBean(
        usageSamplers: List<UsageSampler>,
        usageExporters: List<UsageExporter>
    ): UsageProcessor {
        return if(shouldBatch) {
            BatchingUsageProcessor(usageSamplers.first(), usageExporters.first())
        }  else {
            SimpleUsageProcessor(usageSamplers.first(), usageExporters.first())
        }
    }
}