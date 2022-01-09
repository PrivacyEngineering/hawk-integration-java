package org.datausagetracing.integration.common.usage

import java.util.*

@UsageDslMarker
class UsageBuilderImpl: UsageBuilder {
    override var reference: UUID = UUID.randomUUID()
    override var endpoint: Endpoint = EndpointImpl(emptyMap())
    override var fields: MutableList<Field> = mutableListOf()
    override var cause: Cause = CauseImpl(emptyMap())

    override fun endpoint(endpoint: Endpoint) {
        this.endpoint = this.endpoint.merge(endpoint)
    }

    override fun cause(cause: Cause) {
        this.cause = this.cause.merge(cause)
    }

    override fun build(): Usage = Usage(reference, endpoint, fields, cause)
}