package org.datausagetracing.integration.common.usage


data class Metadata(
    val side: String,
    val phase: String,
    val timestamp: String
): Mergable<Metadata> {
    override fun merge(new: Metadata): Metadata = Metadata(
        new.side.ifEmpty(::side),
        new.phase.ifEmpty(::phase),
        new.timestamp.ifEmpty(::timestamp)
    )
}