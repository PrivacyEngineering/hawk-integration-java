package org.datausagetracing.integration.common.usage

data class Field(
    val type: String,
    val phase: String,
    val origin: String,
    val path: String,
    val count: Int
)