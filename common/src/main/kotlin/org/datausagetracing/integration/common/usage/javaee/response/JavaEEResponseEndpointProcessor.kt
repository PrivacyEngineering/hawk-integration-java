package org.datausagetracing.integration.common.usage.javaee.response

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import org.datausagetracing.integration.common.usage.Processor
import org.datausagetracing.integration.common.usage.UsageContext
import javax.servlet.http.HttpServletResponse

/**
 * Processor that set's $.endpoint.status based on
 * [HttpServletResponse] form Java EE.
 *
 * @author p4skal
 */
class JavaEEResponseEndpointProcessor : Processor<HttpServletResponse> {
    override fun UsageBuilder.process(context: HttpServletResponse, usageContext: UsageContext) {
        endpoint {
            status = context.status.toString()
        }
    }
}