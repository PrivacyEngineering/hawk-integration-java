package org.datausagetracing.integration.spring.example

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ExampleController {
    @GetMapping
    @ResponseBody
    fun index() = "Data Usage Tracing Example"

    @ResponseBody
    @GetMapping("/{ext}")
    fun ext(@PathVariable ext: String) = String(ClassPathResource("test.$ext").inputStream.readAllBytes())
}

data class UserWrapper(val user: User, val count: Int)

data class User(val name: String, val email: String)