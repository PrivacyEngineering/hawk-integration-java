package org.datausagetracing.integration.common.usage

import org.datausagetracing.integration.common.usage.extractor.UsageExtractor

class UsageFactory {
    val extractors = mutableListOf<Pair<Class<*>, UsageExtractor<in UsageContext>>>()

    fun <T : UsageContext> install(contextClass: Class<T>, usageExtractor: UsageExtractor<T>) {
        extractors.add(contextClass to (usageExtractor as UsageExtractor<in UsageContext>))
    }

    fun create(context: UsageContext): Usage {
        val usageBuilder = UsageBuilderImpl()
        usageBuilder.reference = context.reference

        extractors
            .asSequence()
            .filter { it.first.isAssignableFrom(context.javaClass) }
            .forEach { (_, processor) ->
                processor.apply {
                    usageBuilder.extract(context)
                }
            }

        return usageBuilder.build()
    }
}

inline fun <reified T : UsageContext> UsageFactory.install(usageExtractor: UsageExtractor<T>) =
    extractors.add(T::class.java to (usageExtractor as UsageExtractor<in UsageContext>))