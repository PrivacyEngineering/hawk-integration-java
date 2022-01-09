package org.datausagetracing.integration.common.usage

@UsageDslMarker
class CauseBuilder {
    private val map = mutableMapOf<String, Any>()

    fun add(key: String, value: Any) {
        map[key] = value
    }

    fun build(): Cause = CauseImpl(map)
}