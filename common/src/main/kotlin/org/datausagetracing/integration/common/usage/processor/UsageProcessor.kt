package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage

interface UsageProcessor {
    val usageSampler: UsageSampler
    val usageExporter: UsageExporter

    fun processUsage(usage: Usage)
}