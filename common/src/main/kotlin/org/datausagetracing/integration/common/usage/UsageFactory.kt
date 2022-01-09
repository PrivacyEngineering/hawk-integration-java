package org.datausagetracing.integration.common.usage

import java.util.*

class UsageFactory {
    val processors = mutableListOf<Pair<Class<*>, Processor<in Any>>>()

    fun <T : Any> install(contextClass: Class<T>, processor: Processor<T>) {
        processors.add(contextClass to (processor as Processor<in Any>))
    }

    fun create(reference: UUID, context: Any, usageContext: UsageContext): Usage {
        val usageBuilder = UsageBuilderImpl()
        usageBuilder.reference = reference

        processors
            .asSequence()
            .filter { it.first.isAssignableFrom(context.javaClass) }
            .forEach { (_, processor) ->
                processor.apply {
                    usageBuilder.process(context, usageContext)
                }
            }

        return usageBuilder.build()
    }
}

inline fun <reified T : Any> UsageFactory.install(processor: Processor<T>) =
    processors.add(T::class.java to (processor as Processor<in Any>))