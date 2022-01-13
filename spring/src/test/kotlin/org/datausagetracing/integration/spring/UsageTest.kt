package org.datausagetracing.integration.spring

import org.datausagetracing.integration.common.usage.processor.UsageExporter
import org.datausagetracing.integration.common.usage.processor.UsageProcessor
import org.datausagetracing.integration.common.usage.usage
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@SpringBootApplication
@ActiveProfiles("test")
class UsageTest {
    @MockBean
    private lateinit var usageExporter: UsageExporter
    @Autowired
    private lateinit var usageProcessor: UsageProcessor

    @Test
    fun test() {
        usageProcessor.processUsage(usage {  })
        Mockito.verify(usageExporter, Mockito.atLeastOnce()).export(Mockito.anyList())
    }
}