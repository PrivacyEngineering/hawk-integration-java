package org.datausagetracing.integration.common.usage

import org.datausagetracing.integration.common.usage.factory.UsageContext
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.UsageFactory
import org.datausagetracing.integration.common.usage.factory.UsageFactoryBuilder

fun usage(builder: UsageBuilderImpl.() -> Unit) = UsageBuilderImpl().apply(builder).build()

@DslMarker
annotation class UsageDslMarker

fun UsageBuilder.endpoint(builder: EndpointBuilder.() -> Unit) {
    endpoint(EndpointBuilder().apply(builder).build())
}

fun UsageBuilder.field(
    builder: FieldBuilder.() -> Unit
) {
    addField(FieldBuilder().apply(builder).build())
}

fun UsageBuilder.jsonField(builder: FieldBuilder.() -> Unit) =
    field {
        type = "json"
        builder()
    }

fun UsageBuilder.propertiesField(builder: FieldBuilder.() -> Unit) =
    field {
        type = "properties"
        builder()
    }

fun UsageBuilder.yamlField(builder: FieldBuilder.() -> Unit) =
    field {
        type = "yaml"
        builder()
    }

fun UsageBuilder.xmlField(builder: FieldBuilder.() -> Unit) =
    field {
        type = "xml"
        builder()
    }

fun UsageBuilder.cause(builder: CauseBuilder.() -> Unit) =
    cause(CauseBuilder().apply(builder).build())

inline fun <reified T : UsageContext> UsageFactoryBuilder.install(usageExtractor: UsageExtractor<T>) =
    extractors.add(T::class.java to (usageExtractor as UsageExtractor<in UsageContext>))

fun usageFactory(builder: UsageFactoryBuilder.() -> Unit): UsageFactory =
    UsageFactoryBuilder().apply(builder).build()