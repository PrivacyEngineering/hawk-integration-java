package org.datausagetracing.integration.common.usage.processor

import org.datausagetracing.integration.common.usage.Usage
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class BatchingUsageProcessor(
    usageSampler: UsageSampler = AlwaysOnUsageSampler(),
    usageExporter: UsageExporter,
    private val maxBatchEntries: Int = 10,
    private val batchTimeout: Long = 2_000L
) : AbstractUsageProcessor(usageSampler, usageExporter) {
    private var lastBatch = System.currentTimeMillis()
    private var batch: MutableList<Usage> = ArrayList(maxBatchEntries)
    private val lock: Lock = ReentrantLock()

    override fun doProcessUsage(usage: Usage) {
        checkBatch()
        lock.withLock {
            batch.add(usage)
        }
    }

    private fun checkBatch() {
        val oldBatch = lock.withLock {
            val batchTimeoutReached = System.currentTimeMillis() - lastBatch >= batchTimeout
            val batchSizeReached = batch.size >= maxBatchEntries

            if(!batchTimeoutReached && !batchSizeReached) return@checkBatch

            batch.also {
                batch = mutableListOf()
                lastBatch = System.currentTimeMillis()
            }
        }

        export(oldBatch)
    }
}