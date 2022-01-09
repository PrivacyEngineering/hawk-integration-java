package org.datausagetracing.integration.spring

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.datausagetracing.integration.common.usage.HostnameBasedServerProcessor
import org.datausagetracing.integration.common.usage.UsageContextImpl
import org.datausagetracing.integration.common.usage.UsageFactory
import org.datausagetracing.integration.common.usage.install
import org.datausagetracing.integration.common.usage.javaee.request.JavaEERequestEndpointProcessor
import org.datausagetracing.integration.common.usage.javaee.request.JavaEERequestFieldProcessor
import org.datausagetracing.integration.common.usage.javaee.response.JavaEEResponseEndpointProcessor
import org.datausagetracing.integration.common.usage.javaee.response.JavaEEResponseFieldProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.servlet.AsyncHandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class WebTracingInterceptor : AsyncHandlerInterceptor {
    private val attributeName: String = "data-usage-tracing-id"
    private val logger = LogManager.getLogger(javaClass)

    @Value("\${spring.application.name:default}")
    private lateinit var applicationName: String
    private val usageFactory = UsageFactory().apply {
        install(JavaEERequestEndpointProcessor())
        install(JavaEERequestFieldProcessor())
        install(JavaEEResponseEndpointProcessor())
        install(JavaEEResponseFieldProcessor())
        install(HostnameBasedServerProcessor())
    }


    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val uuid = UUID.randomUUID()
        request.setAttribute(attributeName, uuid)

        val usage = usageFactory.create(uuid, request, UsageContextImpl("request"))

        println("usage $usage")

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