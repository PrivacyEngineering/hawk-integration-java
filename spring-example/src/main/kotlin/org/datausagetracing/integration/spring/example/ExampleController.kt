package org.datausagetracing.integration.spring.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {
    @GetMapping
    fun index() = "Data Usage Tracing Example"
}