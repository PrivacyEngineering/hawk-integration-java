package org.datausagetracing.integration.spring.web

import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.processor.UsageExporter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.DispatcherServlet

@Service
@ConditionalOnClass(DispatcherServlet::class)
class RestTemplateUsageExporter: UsageExporter {
    private val restTemplate = RestTemplate()
    @Value("\${datausagetracing.usage.url:http://datausagetracing}")
    private var usageUrl: String = ""

    override fun export(usages: List<Usage>) {
        if(usages.size == 1) {
            restTemplate.postForLocation("$usageUrl/api/usages", usages.first())
        } else {
            restTemplate.postForLocation("$usageUrl/api/usages/batch", usages)
        }
    }
}