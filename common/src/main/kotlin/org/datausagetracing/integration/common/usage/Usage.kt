package org.datausagetracing.integration.common.usage

import java.util.*

data class Usage(
    val reference: UUID,
    val endpoint: Endpoint,
    val fields: List<Field>,
    val cause: Cause
)