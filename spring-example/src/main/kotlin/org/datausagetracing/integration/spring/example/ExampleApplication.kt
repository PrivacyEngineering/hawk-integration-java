package org.datausagetracing.integration.spring.example

import org.datausagetracing.integration.spring.EnableDataUsageTracing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableDataUsageTracing
class ExampleApplication

fun main(args: Array<String>) {
    runApplication<ExampleApplication>(*args)
}
