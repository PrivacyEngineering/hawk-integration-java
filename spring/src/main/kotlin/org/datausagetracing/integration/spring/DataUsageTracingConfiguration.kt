package org.datausagetracing.integration.spring

import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.processor.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.datausagetracing.integration")
open class DataUsageTracingConfiguration {
    private val logger = LogManager.getLogger(javaClass)
    @Value("\${datausagetracing.usage.batch:true}")
    private var shouldBatch: Boolean = true

    init {
        logger.info("Using Data Usage Tracing")
    }

    @Bean
    fun usageProcessorBean(
        usageSamplers: List<UsageSampler>,
        usageExporters: List<UsageExporter>
    ): UsageProcessor {
        println("usageSamplers: $usageSamplers")
        println("usageExporters: $usageExporters")
        return if(shouldBatch) {
            BatchingUsageProcessor(usageSamplers.first(), usageExporters.first())
        }  else {
            SimpleUsageProcessor(usageSamplers.first(), usageExporters.first())
        }
    }
}