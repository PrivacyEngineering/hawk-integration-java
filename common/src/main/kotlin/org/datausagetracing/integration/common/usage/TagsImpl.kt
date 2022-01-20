package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonAnyGetter

data class TagsImpl(
    @get:JsonAnyGetter val map: Map<String, Any>
) : Tags, Map<String, Any> by map {
    override fun merge(new: Tags): Tags = TagsImpl(this + new)
}