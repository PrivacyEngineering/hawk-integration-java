package org.datausagetracing.integration.common.usage.factory.extractor

import org.datausagetracing.integration.common.usage.Field

data class FieldExtractorResult(val format: String, val path: String, val count: Int) {
    fun toField(namespace: String) = Field(format, namespace, path, count)
}