package org.datausagetracing.integration.common.usage.extractor.jackson

import com.fasterxml.jackson.core.JsonPointer
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory

class PropertiesJacksonFieldParser : JacksonFieldParser(JavaPropsFactory()) {
    override fun convertToPath(pointer: JsonPointer): String {
        val builder = StringBuilder()

        pointer.toString().split("/").forEach {
            if(it.toIntOrNull() != null) builder.append("[*]")
            else builder.append(".$it")
        }

        return builder.toString()
    }
}