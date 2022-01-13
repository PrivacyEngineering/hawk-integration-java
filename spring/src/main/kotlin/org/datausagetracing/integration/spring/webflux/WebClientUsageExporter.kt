package org.datausagetracing.integration.spring.webflux

import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.processor.UsageExporter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.DispatcherHandler
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import javax.annotation.PostConstruct

@Service
@ConditionalOnClass(DispatcherHandler::class)
class WebClientUsageExporter : UsageExporter {
    @Value("\${datausagetracing.usage.url:http://datausagetracing}")
    private var usageUrl: String = ""
    private lateinit var webClient: WebClient

    @PostConstruct
    fun initialize() {
        webClient = WebClient.create(usageUrl)
    }

    override fun export(usages: List<Usage>) {
        webClient.post().uri("/api/usages/batch")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(usages)
            .retrieve()
            .toBodilessEntity()
            .block(Duration.ofSeconds(30L))
    }
}