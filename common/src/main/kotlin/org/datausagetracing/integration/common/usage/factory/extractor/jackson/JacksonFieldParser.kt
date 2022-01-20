package org.datausagetracing.integration.common.usage.factory.extractor.jackson

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonPointer
import com.fasterxml.jackson.core.JsonToken

abstract class JacksonFieldParser(protected val jsonFactory: JsonFactory) {
    protected open val valueTokens = setOf(
        JsonToken.VALUE_STRING,
        JsonToken.VALUE_NUMBER_INT,
        JsonToken.VALUE_NUMBER_FLOAT,
        JsonToken.VALUE_TRUE,
        JsonToken.VALUE_FALSE
    )

    abstract val name: String

    open fun parse(content: String): Map<String, Int> {
        val parser = jsonFactory.createParser(content)
        val paths = mutableMapOf<String, Int>()

        while (parser.nextToken() != null) {
            if(parser.currentToken() in valueTokens) {
                val path = convertToPath(parser.parsingContext.pathAsPointer())
                paths.compute(path) { _, oldValue -> oldValue?.plus(1) ?: 1 }
            }
        }

        return paths
    }

    protected open fun convertToPath(pointer: JsonPointer): String = pointer.toString()
}