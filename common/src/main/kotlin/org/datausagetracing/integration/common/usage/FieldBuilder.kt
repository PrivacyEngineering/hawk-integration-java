package org.datausagetracing.integration.common.usage

@UsageDslMarker
open class FieldBuilder {
    var format: String = ""
    var namespace: String = ""
    var path: String = ""
    var count: Int = 0

    fun build() = Field(format, namespace, path, count)
}