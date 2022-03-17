package org.datausagetracing.integration.spring.example

import org.datausagetracing.integration.spring.EnableHawk
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableHawk
@SpringBootApplication
class ExampleApplication

fun main(args: Array<String>) {
    runApplication<ExampleApplication>(*args)
}
