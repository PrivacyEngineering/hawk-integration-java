package org.datausagetracing.integration.common.usage.factory

class UsageFactoryBuilder {
    val extractors = mutableListOf<Pair<Class<*>, UsageExtractor<in UsageContext>>>()

    fun <T : UsageContext> install(contextClass: Class<T>, usageExtractor: UsageExtractor<T>) {
        extractors.add(contextClass to (usageExtractor as UsageExtractor<in UsageContext>))
    }

    fun build(): UsageFactory = UsageFactoryImpl(extractors)
}