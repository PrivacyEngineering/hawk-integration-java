package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonAnyGetter

data class InitiatorImpl(
    @get:JsonAnyGetter val map: Map<String, Any>
) : Initiator, Map<String, Any> by map {
    override fun merge(new: Initiator): Initiator = InitiatorImpl(this + new)
}