package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage

class AlwaysOnUsageSampler : UsageSampler {
    override fun shouldSample(usage: Usage): Boolean = true
}