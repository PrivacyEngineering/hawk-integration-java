package org.datausagetracing.integration.common.usage.javaee.request

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.Processor
import org.datausagetracing.integration.common.usage.UsageContext
import javax.servlet.http.HttpServletRequest

/**
 * Processor that set's $.endpoint.protocol, $.endpoint.method and $.endpoint.path based on
 * [HttpServletRequest] form Java EE.
 *
 * @author p4skal
 */
class JavaEERequestEndpointProcessor : Processor<HttpServletRequest> {
    override fun UsageBuilder.process(context: HttpServletRequest, usageContext: UsageContext) {
        endpoint {
            protocol = context.protocol
            method = context.method
            path = context.requestURI
        }
    }
}