package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonAnyGetter

open class EndpointImpl(
    @get:JsonAnyGetter override val map: Map<String, Any>
) : MapBacked<Endpoint>(::EndpointImpl), Endpoint {
    override fun toString(): String {
        return "EndpointImpl(map=$map)"
    }
}