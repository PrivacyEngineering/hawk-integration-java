package org.datausagetracing.integration.common.usage.factory.extractor

class HttpHeaderFieldExtractor : FieldExtractor<Map<String, List<String>>> {
    override fun extract(content: Map<String, List<String>>): List<FieldExtractorResult> =
        content.map {
            FieldExtractorResult("properties", "/${it.key}", it.value.size)
        }
}