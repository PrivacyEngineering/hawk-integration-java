package org.datausagetracing.integration.common.usage

import java.util.*

interface UsageContext {
    val reference: UUID
    val phase: String
}