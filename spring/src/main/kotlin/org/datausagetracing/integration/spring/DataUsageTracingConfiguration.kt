package org.datausagetracing.integration.spring

import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.datausagetracing.integration")
class DataUsageTracingConfiguration {
    private val logger = LogManager.getLogger(javaClass)

    init {
        logger.info("Using Data Usage Tracing")
    }
}