package org.datausagetracing.integration.common.usage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class UsageTest {
    @Test
    fun `check if usage builder produces valid usage`() {
        val usage = usage {
            endpoint {
                service = "test"
                protocol = "HTTP/1.1"
                method = "POST"
                path = "/api/user/4"
                status = "200"
                add("custom1", "custom_xy")
            }
            field {
                type = "json"
                phase = "request"
                origin = "body"
                path = "$.[*].name"
                count = 3
            }
            propertiesField {
                phase = "request"
                origin = "header"
                path = "name"
                count = 1
            }
            cause {
                add("custom2", "yx")
            }
        }

        assertEquals("test", usage.endpoint["service"],)
        assertEquals("HTTP/1.1", usage.endpoint["protocol"])
        assertEquals("POST", usage.endpoint["method"])
        assertEquals("/api/user/4", usage.endpoint["path"])
        assertEquals("200", usage.endpoint["status"])
        assertEquals("custom_xy", usage.endpoint["custom1"])

        assertEquals("json", usage.fields[0].type)
        assertEquals("request", usage.fields[0].phase)
        assertEquals("body", usage.fields[0].origin)
        assertEquals("$.[*].name", usage.fields[0].path)
        assertEquals(3, usage.fields[0].count)

        assertEquals("properties", usage.fields[1].type)
        assertEquals("request", usage.fields[1].phase)
        assertEquals("header", usage.fields[1].origin)
        assertEquals("name", usage.fields[1].path)
        assertEquals(1, usage.fields[1].count)

        assertEquals("yx", usage.cause["custom2"])
    }

}