package org.datausagetracing.integration.common.usage

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@UsageDslMarker
open class MetadataBuilder {
    var side: String = ""
    var phase: String = ""
    var timestamp: String = ""

    fun timestamp(timestamp: ZonedDateTime) {
        this.timestamp = timestamp.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    fun build() = Metadata(side, phase, timestamp)
}