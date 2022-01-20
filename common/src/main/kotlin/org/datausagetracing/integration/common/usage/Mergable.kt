package org.datausagetracing.integration.common.usage

interface Mergable<T> {
    fun merge(new: T): T
}