package org.datausagetracing.integration.common.usage

interface Mergable<T> {
    fun merge(other: T): T
}