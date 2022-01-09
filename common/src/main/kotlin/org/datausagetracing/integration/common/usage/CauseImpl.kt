package org.datausagetracing.integration.common.usage

import com.fasterxml.jackson.annotation.JsonAnyGetter

open class CauseImpl(
    @get:JsonAnyGetter override val map: Map<String, Any>
) : MapBacked<Cause>(::CauseImpl), Cause {
    override fun toString(): String {
        return "CauseImpl(map=$map)"
    }
}