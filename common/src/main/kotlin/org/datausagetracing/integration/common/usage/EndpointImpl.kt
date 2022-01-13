package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonAnyGetter

data class EndpointImpl(
    @get:JsonAnyGetter val map: Map<String, Any>
) : Endpoint, Map<String, Any> by map {
    override fun merge(other: Endpoint): Endpoint = EndpointImpl(this + other)
}