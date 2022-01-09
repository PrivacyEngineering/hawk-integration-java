package org.datausagetracing.integration.common.usage

@UsageDslMarker
open class FieldBuilder {
    var type: String = ""
    var phase: String = ""
    var origin: String = ""
    var path: String = ""
    var count: Int = 0

    fun build() = Field(type, phase, origin, path, count)
}