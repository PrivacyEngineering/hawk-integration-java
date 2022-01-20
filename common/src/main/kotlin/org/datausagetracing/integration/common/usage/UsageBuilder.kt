package org.datausagetracing.integration.common.usage

import java.util.*

interface UsageBuilder {
    var id: UUID
    val metadata: Metadata
    val endpoint: Endpoint
    val initiator: Initiator
    val fields: MutableList<Field>
    val tags: Tags

    fun metadata(metadata: Metadata)

    fun endpoint(endpoint: Endpoint)

    fun initiator(initiator: Initiator)

    fun addField(field: Field) = fields.add(field)

    fun tags(tags: Tags)

    fun build(): Usage
}