package org.datausagetracing.integration.common.usage.javaee.request

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.propertiesField
import org.datausagetracing.integration.common.usage.Processor
import org.datausagetracing.integration.common.usage.UsageContext
import javax.servlet.http.HttpServletRequest

/**
 * Processor that set's $.endpoint.protocol, $.endpoint.method and $.endpoint.path based on
 * [HttpServletRequest] form Java EE.
 *
 * @author p4skal
 */
class JavaEERequestFieldProcessor : Processor<HttpServletRequest> {
    override fun UsageBuilder.process(context: HttpServletRequest, usageContext: UsageContext) {
        // TODO: Filter header names, to only include custom headers
        context.headerNames
            .asSequence()
            .associateWith(context::getHeaders)
            .forEach { (name, value) ->
                propertiesField {
                    phase = usageContext.phase
                    origin = "header"
                    path = name
                    count = value.asSequence().count()
                }
            }
    }
}