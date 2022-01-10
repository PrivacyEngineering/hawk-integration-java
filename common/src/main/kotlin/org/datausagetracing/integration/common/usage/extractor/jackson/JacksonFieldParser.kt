package org.datausagetracing.integration.common.usage.extractor.jackson

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

    open fun parse(content: String): Set<String> {
        val parser = jsonFactory.createParser(content)
        val paths = mutableSetOf<String>()

        while (parser.nextToken() != null) {
            if(parser.currentToken() in valueTokens) {
                paths.add(convertToPath(parser.parsingContext.pathAsPointer()))
            }
        }

        return paths
    }

    protected open fun convertToPath(pointer: JsonPointer): String = pointer.toString()
}