package org.datausagetracing.integration.spring.example

import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ExampleController {
    @GetMapping
    @ResponseBody
    fun index() = "Data Usage Tracing Example"

    @ResponseBody
    @GetMapping( "/json", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun json(): ByteArray = ClassPathResource("test.json").inputStream.readAllBytes()

    @ResponseBody
    @GetMapping( "/properties", produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun properties(): ByteArray = ClassPathResource("test.properties").inputStream.readAllBytes()

    @ResponseBody
    @GetMapping( "/xml", produces = [MediaType.APPLICATION_XML_VALUE])
    fun xml(): ByteArray = ClassPathResource("test.xml").inputStream.readAllBytes()

    @ResponseBody
    @GetMapping( "/yaml", produces = ["application/yaml"])
    fun yaml(): ByteArray = ClassPathResource("test.yaml").inputStream.readAllBytes()


}

data class UserWrapper(val user: User, val count: Int)

data class User(val name: String, val email: String)