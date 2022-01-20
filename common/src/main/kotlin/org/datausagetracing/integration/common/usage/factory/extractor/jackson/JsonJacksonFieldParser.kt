package org.datausagetracing.integration.common.usage.factory.extractor.jackson

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonPointer

class JsonJacksonFieldParser : JacksonFieldParser(JsonFactory()) {
    override val name = "json"

    override fun convertToPath(pointer: JsonPointer): String = pointer.toString()
        .split('/')
        .joinToString(".", "$") {
            if (it.toIntOrNull() != null) "[*]" else it
        }
}