package org.datausagetracing.integration.common.usage

import java.util.*

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


val usage = usage {
    reference = UUID.randomUUID()
    endpoint {
        protocol = "HTTP"
        method = "POST"
        path = "/api/user/create"
    }
    jsonField {
        path = "$.[*].user.email"
        count = 1
    }
    jsonField {
        path = "$.[*].user.lastName"
        count = 1
    }
    cause {
        add("newsletterId", 32)
    }
}