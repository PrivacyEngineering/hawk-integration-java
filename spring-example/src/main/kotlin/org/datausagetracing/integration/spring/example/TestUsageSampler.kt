package org.datausagetracing.integration.spring.example

import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.processor.UsageSampler
import org.springframework.stereotype.Service

@Service
class TestUsageSampler: UsageSampler {
    override fun shouldSample(usage: Usage): Boolean {
        return true
    }
}