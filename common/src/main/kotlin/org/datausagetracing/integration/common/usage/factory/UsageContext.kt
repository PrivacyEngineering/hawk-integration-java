package org.datausagetracing.integration.common.usage.factory

import java.util.*

interface UsageContext {
    val reference: UUID
    val phase: String
}