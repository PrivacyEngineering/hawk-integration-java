package org.datausagetracing.integration.common.usage.factory.extractor

interface FieldExtractor<C: Any> {
    fun extract(content: C): List<FieldExtractorResult>
}