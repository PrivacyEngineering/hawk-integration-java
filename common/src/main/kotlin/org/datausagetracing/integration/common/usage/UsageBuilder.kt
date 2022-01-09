package org.datausagetracing.integration.common.usage

import java.util.*

interface UsageBuilder {
    var reference: UUID
    val endpoint: Endpoint
    val fields: MutableList<Field>
    val cause: Cause

    fun endpoint(endpoint: Endpoint)

    fun addField(field: Field) = fields.add(field)

    fun cause(cause: Cause)

    fun build(): Usage
}