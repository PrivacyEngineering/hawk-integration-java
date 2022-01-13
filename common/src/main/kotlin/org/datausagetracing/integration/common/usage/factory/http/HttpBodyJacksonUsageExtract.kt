package org.datausagetracing.integration.common.usage.factory.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.factory.jackson.AbstractJacksonUsageExtractor
import org.datausagetracing.integration.common.usage.field

open class HttpBodyJacksonUsageExtract : AbstractJacksonUsageExtractor<HttpUsageContext>() {
    override fun UsageBuilder.extract(context: HttpUsageContext) {
        parse(context.body).forEach { (discoveredType, paths) ->
            paths.forEach { (discoveredPath, discoveredCount) ->
                field {
                    type = discoveredType
                    path = discoveredPath
                    count = discoveredCount
                    phase = context.phase
                    origin = "body"
                }
            }
        }
    }
}