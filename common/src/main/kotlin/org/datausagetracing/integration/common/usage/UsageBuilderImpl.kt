package org.datausagetracing.integration.common.usage

import java.util.*

@UsageDslMarker
class UsageBuilderImpl : UsageBuilder {
    override var id: UUID = UUID.randomUUID()
    override var metadata: Metadata = Metadata("", "", "")
    override var endpoint: Endpoint = EndpointImpl(emptyMap())
    override var initiator: Initiator = InitiatorImpl(emptyMap())
    override var fields: MutableList<Field> = mutableListOf()
    override var tags: Tags = TagsImpl(emptyMap())

    override fun metadata(metadata: Metadata) {
        this.metadata = this.metadata.merge(metadata)
    }

    override fun endpoint(endpoint: Endpoint) {
        this.endpoint = this.endpoint.merge(endpoint)
    }

    override fun initiator(initiator: Initiator) {
        this.initiator = this.initiator.merge(initiator)
    }

    override fun tags(tags: Tags) {
        this.tags = this.tags.merge(tags)
    }

    override fun build(): Usage = Usage(id, metadata, endpoint, initiator, fields, tags)
}