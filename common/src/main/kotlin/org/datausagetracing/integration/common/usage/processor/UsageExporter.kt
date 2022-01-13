package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage

interface UsageExporter {
    @Throws(Throwable::class)
    fun export(usages: List<Usage>)
}