# Hawk Integration Java

![workflow](https://github.com/PrivacyEngineering/hawk-integration-java/actions/workflows/main.yml/badge.svg)

This projects features the integration of the Hawk features for the Java side.
It is written in Kotlin with full compatibility to Java using Spring Boot and communicates with
the [Hawk Service](https://github.com/PrivacyEngineering/hawk-service).
The current implementation contains a Spring Boot Web integration for HTTP. In the future more
frameworks might be added. To rapidly implement a custom framework there is the common module, which
serves as a base of the protocol.

## Concept

The idea of the Java Integration is to intercept the traffic of the application and extract the
atomic
data references (Usages) and sample / batch / export them to the Hawk Service.
The benefit of using the Java Integration is that you can track encrypted traffic or in the future
even traffic to external api's.

## Spring

If you have a Spring Boot application, and you are using Spring Boot Web (not WebFlux) the easiest
way is this module.

### 1) Install

You need to add the following dependency to your build management tool:

Maven:

```xml
<dependency>
  <groupId>org.datausagetracing.integration</groupId>
  <artifactId>spring</artifactId>
  <version>VERSION</version>
</dependency>
```

Gradle (Groovy):

```groovy
implementation 'org.datausagetracing.integration:spring:VERSION'
```

Gradle (Kotlin):

```kotlin
implementation("org.datausagetracing.integration:spring:VERSION")
```

Just replace `VERSION`
with [the version you like](https://github.com/PrivacyEngineering/hawk-integration-java/packages/1309269)
.

### 2) Enable

Annotate your main class / Spring Boot Class with `@EnableHawk`

```kotlin
package org.datausagetracing.integration.spring.example

import org.datausagetracing.integration.spring.EnableHawk
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableHawk
@SpringBootApplication
class ExampleApplication
```

### 3) Configure

This module uses Spring Configuration to configure itself.
There are the following options:

| Name in *.properties     | Env Name                  | Default                   | Description                                          |
|--------------------------|---------------------------|---------------------------|------------------------------------------------------|
| hawk.usage.url           | HAWK_USAGE_URL            | `http://datausagetracing` | The URL of the Hawk Service                          |
| hawk.usage.batch         | HAWK_USAGE_BATCH          | `true`                    | Buffers usages and sends them in one large request   |
| hawk.usage.sampler.ratio | HAWK_USAGE_SAMPLER_RATION | `1.0`                     | Percent of Usage's to send. 1.0 = all, 0.0 = nothing |

For details, see [Common Section](#common).

For custom `UsageSampler`, `UsageExporter`, `UsageProcessor` implementations, just mark the class as
a Spring
component and add a `javax.annotation.Priority` annotation. When overriding `UsageProcessor` you may
break some configuration options. You are also required to obtain the `UsageSampler`
/ `UsageExporter` yourself.

## Common

The common module declares the structure of the Usage in an object-oriented fashion.
These objects are to be serialized with some JSON ORM
like [Jackson](https://github.com/FasterXML/jackson-databind) and can not be used for
deserialization. The `Usage` class consists of the following fields: `id: UUID` `metadata: Metadata`
, `endpoint: Endpoint`, `initatior: Initiator`, `fields: List<Field>`, `tags: Tags`. Every field
except for id and fields have their own build, which should be used to instantiate them.
To build the `Usage`, the `UsageBuilder` is used. All of these `Builder`-classes are mergeable with
other builders of the same type. Some classes are backed by a map, which allows them to take
properties. This offers flexibility in filling the `UsageBuilder`. The Usage has
support for Kotlin DSL.

An example usage might look like this:

```kotlin
val usage = usage {
    metadata {
        side = "server"
        phase = "request"
        timestamp(ZonedDateTime.now())
    }
    endpoint {
        host = "test"
        protocol = "HTTP/1.1"
        method = "POST"
        path = "/api/user/4"
        status = "200"
        add("custom1", "custom_xy")
    }
    field {
        format = "json"
        namespace = "body"
        path = "$.[*].name"
        count = 3
    }
    propertiesField {
        namespace = "header"
        path = "name"
        count = 1
    }
    tags {
        add("custom2", "yx")
    }
}
```

### Factory

To make the creation of big Usage's cleaner and more modular the `UsageFactory` comes into place.
The `UsageContext` can be implemented to pass custom data into the Usage-Creation process. Default
implementations are `JavaEEHttpRequestUsageContext` and `JavaEEHttpResponseUsageContext` which wrap
around the `javax.servlet` Request / Response implementations.
The modularity aspect comes from the `UsageExtractor` which allows to fill the `UsageBuilder`
instance
incrementally, by getting the `UsageContext` and the mutable `UsageBuilder` instance.
There are many default implementations for the HTTP Protocol, which can be used when
the `UsageContext`
implements either `HttpRequestUsageContext` or `HttpResponseUsageContext`. Just look inside
the `org.datausagetracing.integration.common.usage.factory.extractor` package for implementations.
The `UsageFactory` instance takes a list of extractors and builds a `Usage` instance based on the
passed context. It is recommended to use the `UsageFactoryBuilder` to create the `UsageFactory` in a
cleaner way. The `UsageFactoryBuilder` also supports Kotlin DSL.

Here is an example of creating a Usage:

```kotlin

// In the initializer
val usageFactory = requestUsageFactory {
    install(HttpRequestEndpointUsageExtractor())
    install(HttpRequestInitiatorUsageExtractor())
    install(HttpFieldUsageExtractor(bodyFieldExtractor = JacksonFieldExtractor()))
    install(MetadataUsageExtractor())
}

// Every request (can be async)
val reference = UUID.random() // Or take it from the request header
val usage = requestUsageFactory.invoke(
    JavaEEHttpRequestUsageContext(
        request,
        reference,
        LocalDateTime.now()
    )
)
```

### Processing

To sample, batch and export the generated Usages, the `UsageSampler`, `UsageProcessor`
and `UsageExporter` classes are used.
The `UsageSampler` can be implemented to determine whether a `Usage` should be included or not.
Default implementations are the `IdRatioUsageSampler`, which takes in how many percent of Usages
should be exported (between 0.0 = none and 1.0 = all) and the `AlwaysOnUsageSampler`, which exports
every Usage.
The business-logic to export the Usages and send them to the Hawk-Service, is implemented inside the
implementation of the `UsageExporter` interface. The `UsageProcessor` receives every Usage and
samples and exports it, using the other classes. Default implementations are
the `BatchingUsageProcessor`, which buffer's multiple Usage's and exports them in one 'Batch' and
the `SimpleUsageProcessor` which simply samples and export every Usage.

See the `spring` module for an example.


