package org.datausagetracing.integration.common.usage.factory.extractor.http

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.FieldExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.HttpHeaderFieldExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.jackson.JacksonFieldExtractor
import org.datausagetracing.integration.common.usage.factory.http.HttpUsageContext

open class HttpFieldUsageExtractor(
    protected val headerFieldExtractor: FieldExtractor<Map<String, List<String>>> = HttpHeaderFieldExtractor(),
    protected val bodyFieldExtractor: FieldExtractor<String> = JacksonFieldExtractor()
) : UsageExtractor<HttpUsageContext> {
    override fun UsageBuilder.extract(context: HttpUsageContext) {
        headerFieldExtractor
            .extract(context.headers)
            .map { it.toField("header") }
            .forEach(::addField)
        bodyFieldExtractor
            .extract(context.body)
            .map { it.toField("body") }
            .forEach(::addField)
    }
}