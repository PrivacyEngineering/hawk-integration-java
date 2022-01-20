package org.datausagetracing.integration.common.usage.factory

import org.datausagetracing.integration.common.usage.Usage
import org.datausagetracing.integration.common.usage.UsageBuilderImpl

class UsageFactoryImpl(
    private val extractors: List<Pair<Class<*>, UsageExtractor<in UsageContext>>>
): UsageFactory {

    override operator fun invoke(context: UsageContext): Usage {
        val usageBuilder = UsageBuilderImpl()
        usageBuilder.id = context.id

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