package org.datausagetracing.integration.spring

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.Cause
import org.datausagetracing.integration.common.Field
import org.datausagetracing.integration.common.HttpEndpoint
import org.datausagetracing.integration.common.Usage
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class WebTracingInterceptor : HandlerInterceptorAdapter() {
    private val attributeName: String = "data-usage-tracing-id"
    private val logger = LogManager.getLogger(javaClass)

    @Value("\${spring.application.name:default}")
    private lateinit var applicationName: String

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val uuid = UUID.randomUUID()
        request.setAttribute(attributeName, uuid)

        val endpoint = HttpEndpoint(
            applicationName,
            "HTTP",
            request.method,
            request.requestURI
        )

        val commonHeaders = HttpHeaders().keys

        val headerFields = request.headerNames
            .asSequence()
            .filterNot { it in commonHeaders }
            .associateWith { request.getHeaders(it).asSequence().count() }
            .map { Field("properties", it.key, it.value) }

        val cause = object : Cause {}

        val usage = Usage(uuid, endpoint, headerFields, cause)

        logger.info("Inserting ${ObjectMapper().writeValueAsString(usage)}")

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val uuid = request.getAttribute(attributeName)

        // build response
    }
}