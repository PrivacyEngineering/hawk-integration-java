package org.datausagetracing.integration.common.usage.factory.jackson

import com.fasterxml.jackson.core.JsonToken.*
import org.datausagetracing.integration.common.usage.factory.UsageContext
import org.datausagetracing.integration.common.usage.factory.UsageExtractor

abstract class AbstractJacksonUsageExtractor<C : UsageContext>: UsageExtractor<C> {
    private val valueTokens =
        setOf(VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE)

    protected fun parse(content: String): Map<String, Map<String, Int>> {
        val result = mutableMapOf<String, Map<String, Int>>()
        try {
            result["json"] = JsonJacksonFieldParser().parse(content)
        } catch (throwable: Throwable) {
        }
        try {
            result["properties"] = PropertiesJacksonFieldParser().parse(content)
        } catch (throwable: Throwable) {
        }
        try {
            result["xml"] = XmlJacksonFieldParser().parse(content)
        } catch (throwable: Throwable) {
        }
        try {
            result["yaml"] = YamlJacksonFieldParser().parse(content)
        } catch (throwable: Throwable) {
        }

        return result
    }
}