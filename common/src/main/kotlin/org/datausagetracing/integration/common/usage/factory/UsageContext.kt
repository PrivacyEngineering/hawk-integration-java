package org.datausagetracing.integration.common.usage.factory

import java.time.LocalDateTime
import java.util.*

interface UsageContext {
    val id: UUID
    val side: Side
    val phase: Phase
    val timestamp: LocalDateTime
}

