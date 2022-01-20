package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage
import kotlin.math.abs

class ReferenceRatioUsageSampler(ratio: Double) : UsageSampler {
    private val referenceUpperBound = when (ratio) {
        0.0 -> Long.MIN_VALUE
        1.0 -> Long.MAX_VALUE
        else -> ratio.times(Long.MAX_VALUE).toLong()
    }

    override fun shouldSample(usage: Usage): Boolean =
        abs(usage.id.mostSignificantBits) <= referenceUpperBound
}