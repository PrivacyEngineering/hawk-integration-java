package org.datausagetracing.integration.common.usage

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
    CauseBuilder().apply(builder).build()

fun usageFactory(builder: UsageFactory.() -> Unit): UsageFactory = UsageFactory().also(builder)