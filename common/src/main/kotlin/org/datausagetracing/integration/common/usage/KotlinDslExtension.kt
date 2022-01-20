package org.datausagetracing.integration.common.usage

import org.datausagetracing.integration.common.usage.factory.UsageContext
import org.datausagetracing.integration.common.usage.factory.UsageExtractor
import org.datausagetracing.integration.common.usage.factory.UsageFactory
import org.datausagetracing.integration.common.usage.factory.UsageFactoryBuilder

fun usage(builder: UsageBuilderImpl.() -> Unit) = UsageBuilderImpl().apply(builder).build()

@DslMarker
annotation class UsageDslMarker

fun UsageBuilder.metadata(builder: MetadataBuilder.() -> Unit) {
    metadata(MetadataBuilder().apply(builder).build())
}

fun UsageBuilder.endpoint(builder: EndpointBuilder.() -> Unit) {
    endpoint(EndpointBuilder().apply(builder).build())
}

fun UsageBuilder.initiator(builder: InitiatorBuilder.() -> Unit) {
    initiator(InitiatorBuilder().apply(builder).build())
}

fun UsageBuilder.field(
    builder: FieldBuilder.() -> Unit
) {
    addField(FieldBuilder().apply(builder).build())
}

fun UsageBuilder.jsonField(builder: FieldBuilder.() -> Unit) =
    field {
        format = "json"
        builder()
    }

fun UsageBuilder.propertiesField(builder: FieldBuilder.() -> Unit) =
    field {
        format = "properties"
        builder()
    }

fun UsageBuilder.yamlField(builder: FieldBuilder.() -> Unit) =
    field {
        format = "yaml"
        builder()
    }

fun UsageBuilder.xmlField(builder: FieldBuilder.() -> Unit) =
    field {
        format = "xml"
        builder()
    }

fun UsageBuilder.tags(builder: TagsBuilder.() -> Unit) =
    tags(TagsBuilder().apply(builder).build())

inline fun <reified T : UsageContext> UsageFactoryBuilder.install(usageExtractor: UsageExtractor<T>) =
    extractors.add(T::class.java to (usageExtractor as UsageExtractor<in UsageContext>))

fun usageFactory(builder: UsageFactoryBuilder.() -> Unit): UsageFactory =
    UsageFactoryBuilder().apply(builder).build()