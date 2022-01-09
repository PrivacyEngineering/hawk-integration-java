package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class MapBacked<T>(
    @JsonIgnore
    val constructor: (Map<String, Any>) -> T
) : Mergable<T> {
    open val map: Map<String, Any> = mutableMapOf()

    override fun merge(other: T) = constructor(map + (other as MapBacked<*>).map)
}