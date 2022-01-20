package org.datausagetracing.integration.common.usage.factory.extractor.jackson

import com.fasterxml.jackson.core.JsonToken.*
import org.datausagetracing.integration.common.usage.factory.extractor.FieldExtractor
import org.datausagetracing.integration.common.usage.factory.extractor.FieldExtractorResult

open class JacksonFieldExtractor(
    protected val fieldParsers: List<JacksonFieldParser> = listOf(
        JsonJacksonFieldParser(),
//        PropertiesJacksonFieldParser(),
//        XmlJacksonFieldParser(),
//        YamlJacksonFieldParser()
    )
) : FieldExtractor<String> {
    protected val valueTokens =
        setOf(VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE)

    override fun extract(content: String): List<FieldExtractorResult> =
        fieldParsers.flatMap { parser ->
            try {
                return@flatMap parser.parse(content).map {
                    FieldExtractorResult(parser.name, it.key, it.value)
                }
            } catch (throwable: Throwable) {
            }
            return@flatMap emptyList()
        }
}