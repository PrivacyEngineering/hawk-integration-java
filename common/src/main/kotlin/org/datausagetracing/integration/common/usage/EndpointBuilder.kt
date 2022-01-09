package org.datausagetracing.integration.common.usage

@UsageDslMarker
class EndpointBuilder {
    private val map = mutableMapOf<String, Any>()
    var service: String by map
    var protocol: String by map
    var method: String by map
    var path: String by map
    var status: String by map

    fun add(key: String, value: Any) {
        map[key] = value
    }

    fun build(): Endpoint = EndpointImpl(map)
}