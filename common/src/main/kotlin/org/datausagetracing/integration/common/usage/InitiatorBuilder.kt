package org.datausagetracing.integration.common.usage

@UsageDslMarker
class InitiatorBuilder {
    private val map = mutableMapOf<String, Any>()
    var host: String by map

    fun add(key: String, value: Any) {
        map[key] = value
    }

    fun build(): Initiator = InitiatorImpl(map)
}