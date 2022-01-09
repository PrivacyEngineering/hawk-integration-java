package org.datausagetracing.integration.common.usage.javaee.response

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.propertiesField
import org.datausagetracing.integration.common.usage.Processor
import org.datausagetracing.integration.common.usage.UsageContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Processor that set's $.endpoint.protocol, $.endpoint.method and $.endpoint.path based on
 * [HttpServletRequest] form Java EE.
 *
 * @author p4skal
 */
class JavaEEResponseFieldProcessor : Processor<HttpServletResponse> {
    override fun UsageBuilder.process(context: HttpServletResponse, usageContext: UsageContext) {
        // TODO: Filter header names, to only include custom headers
        context.headerNames
            .asSequence()
            .associateWith(context::getHeaders)
            .forEach { (name, value) ->
                propertiesField {
                    phase = usageContext.phase
                    origin = "header"
                    path = name
                    count = value.count()
                }
            }
    }
}