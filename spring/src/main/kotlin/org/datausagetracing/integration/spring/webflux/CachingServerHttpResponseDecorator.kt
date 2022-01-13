package org.datausagetracing.integration.spring.webflux

import org.reactivestreams.Publisher
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.function.Function

class CachingServerHttpResponseDecorator(delegate: ServerHttpResponse, private val callback: CachingServerHttpResponseDecorator.() -> Unit) :
    ServerHttpResponseDecorator(delegate) {
    private val dataBuffers: MutableList<DataBuffer> = mutableListOf()
    var content: String? = null

    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
        println("write with")
        Flux.from(body).doOnNext {
            println("add $it")
            dataBuffers.add(it)
        }

        delegate.writeWith(body)
        return Mono.empty()
    }

    override fun writeAndFlushWith(body: Publisher<out Publisher<out DataBuffer>>): Mono<Void> {
        writeWith(Flux.from(body).flatMapSequential(Function.identity()))
        Flux.from(body).last().doOnNext { lastPublisher ->
            println("got last publisher")
            Flux.from(lastPublisher).last().doOnNext { _ ->
                println("got last")
                content = dataBuffers.joinToString("") {
                    val content = StandardCharsets.UTF_8.decode(it.asByteBuffer()).toString()
                    DataBufferUtils.release(it)
                    content
                }
                println("got content $content")
                callback()
            }
        }
        return Mono.empty()
    }

}