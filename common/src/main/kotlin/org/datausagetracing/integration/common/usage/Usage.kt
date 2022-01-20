package org.datausagetracing.integration.common.usage

import java.util.*

data class Usage(
    val id: UUID,
    val metadata: Metadata,
    val endpoint: Endpoint,
    val initiator: Initiator,
    val fields: List<Field>,
    val tags: Tags
)