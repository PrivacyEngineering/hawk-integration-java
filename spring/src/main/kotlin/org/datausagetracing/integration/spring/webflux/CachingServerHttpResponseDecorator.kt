package org.datausagetracing.integration.spring.webflux

import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator

class CachingServerHttpResponseDecorator(delegate: ServerHttpResponse) :
    ServerHttpResponseDecorator(delegate) {

}