package org.datausagetracing.integration.common.usage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime


class UsageTest {
    @Test
    fun `check if usage builder produces valid usage`() {
        val usage = usage {
            metadata {
                side = "server"
                phase = "request"
                timestamp(ZonedDateTime.now())
            }
            endpoint {
                host = "test"
                protocol = "HTTP/1.1"
                method = "POST"
                path = "/api/user/4"
                status = "200"
                add("custom1", "custom_xy")
            }
            field {
                format = "json"
                namespace = "body"
                path = "$.[*].name"
                count = 3
            }
            propertiesField {
                namespace = "header"
                path = "name"
                count = 1
            }
            tags {
                add("custom2", "yx")
            }
        }

        assertEquals("test", usage.endpoint["host"],)
        assertEquals("HTTP/1.1", usage.endpoint["protocol"])
        assertEquals("POST", usage.endpoint["method"])
        assertEquals("/api/user/4", usage.endpoint["path"])
        assertEquals("200", usage.endpoint["status"])
        assertEquals("custom_xy", usage.endpoint["custom1"])

        assertEquals("json", usage.fields[0].format)
        assertEquals("body", usage.fields[0].namespace)
        assertEquals("$.[*].name", usage.fields[0].path)
        assertEquals(3, usage.fields[0].count)

        assertEquals("properties", usage.fields[1].format)
        assertEquals("name", usage.fields[1].path)
        assertEquals(1, usage.fields[1].count)

        assertEquals("yx", usage.tags["custom2"])
    }

}