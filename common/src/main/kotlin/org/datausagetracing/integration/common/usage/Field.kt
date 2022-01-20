package org.datausagetracing.integration.common.usage

data class Field(
    val format: String,
    val namespace: String,
    val path: String,
    val count: Int
)