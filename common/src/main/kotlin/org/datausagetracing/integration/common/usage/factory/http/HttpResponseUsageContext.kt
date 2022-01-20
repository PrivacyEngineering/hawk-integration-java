package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.factory.Phase

interface HttpResponseUsageContext : HttpUsageContext {
    override val phase: Phase get() = Phase.RESPONSE
    val status: Int
}