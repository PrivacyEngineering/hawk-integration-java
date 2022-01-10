package org.datausagetracing.integration.common.usage.extractor

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.propertiesField

open class HttpHeaderUsageExtract : UsageExtractor<HttpUsageContext> {
    override fun UsageBuilder.extract(context: HttpUsageContext) {
        context.headers.forEach { (name, values) ->
            propertiesField {
                phase = context.phase
                origin = "header"
                path = name
                count = values.size
            }
        }
    }
}