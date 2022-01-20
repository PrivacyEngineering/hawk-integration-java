package org.datausagetracing.integration.common.usage

@UsageDslMarker
class TagsBuilder {
    private val map = mutableMapOf<String, Any>()

    fun add(key: String, value: Any) {
        map[key] = value
    }

    fun build(): Tags = TagsImpl(map)
}