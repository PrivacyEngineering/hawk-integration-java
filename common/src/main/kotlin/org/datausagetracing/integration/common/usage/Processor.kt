package org.datausagetracing.integration.common.usage

interface Processor<C : Any> {
    fun UsageBuilder.process(context: C, usageContext: UsageContext)
}