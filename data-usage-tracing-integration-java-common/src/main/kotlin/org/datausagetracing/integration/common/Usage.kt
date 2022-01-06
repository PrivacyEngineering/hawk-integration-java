package org.datausagetracing.integration.common

import java.util.*

/**
 * Immutable representation of a Data Usage.
 *
 * @author p4skal
 */
data class Usage(val uuid: UUID, val endpoint: Endpoint, val fields: List<Field>, val cause: Cause)