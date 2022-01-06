package org.datausagetracing.integration.common

interface Endpoint

data class HttpEndpoint(
    val service: String,
    val type: String,
    val method: String,
    val path: String
) : Endpoint