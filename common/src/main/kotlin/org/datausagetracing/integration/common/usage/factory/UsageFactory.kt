package org.datausagetracing.integration.common.usage.factory

import org.datausagetracing.integration.common.usage.Usage

interface UsageFactory {
    operator fun invoke(context: UsageContext): Usage
}