package org.datausagetracing.integration.spring

import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.processor.ReferenceRatioUsageSampler
import org.datausagetracing.integration.common.usage.processor.UsageSampler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ConfigurationBasedSampler : UsageSampler {
    @Value("\${datausagetracing.usage.sampler.ratio:1}")
    private var targetRatio: String = "1.0"
    private lateinit var sampler: UsageSampler

    @PostConstruct
    fun initialize() {
        sampler = ReferenceRatioUsageSampler(targetRatio.toDoubleOrNull() ?: 1.0)
    }

    override fun shouldSample(usage: Usage): Boolean {
        return sampler.shouldSample(usage)
    }
}