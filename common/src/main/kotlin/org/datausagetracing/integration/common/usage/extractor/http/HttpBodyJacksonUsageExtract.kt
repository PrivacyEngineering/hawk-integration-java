package org.datausagetracing.integration.common.usage.extractor.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.extractor.jackson.AbstractJacksonUsageExtractor
import org.datausagetracing.integration.common.usage.field

open class HttpBodyJacksonUsageExtract : AbstractJacksonUsageExtractor<HttpUsageContext>() {
    override fun UsageBuilder.extract(context: HttpUsageContext) {
        parse(context.body).forEach { (discoveredType, paths) ->
            paths.forEach { discoveredPath ->
                field {
                    type = discoveredType
                    path = discoveredPath
                    count = 1
                    phase = context.phase
                    origin = "body"
                }
            }
        }
    }
}