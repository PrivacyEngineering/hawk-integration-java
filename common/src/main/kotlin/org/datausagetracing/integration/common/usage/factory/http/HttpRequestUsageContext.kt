package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.factory.Phase

interface HttpRequestUsageContext : HttpUsageContext {
    override val phase: Phase get() = Phase.REQUEST
    val protocol: String
    val method: String
    val path: String
    val remoteHost: String
}