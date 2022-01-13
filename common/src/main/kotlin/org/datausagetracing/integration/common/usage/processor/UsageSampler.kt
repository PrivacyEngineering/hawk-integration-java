package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage

interface UsageSampler {
    fun shouldSample(usage: Usage): Boolean
}