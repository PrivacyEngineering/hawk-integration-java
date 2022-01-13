package org.datausagetracing.integration.common.usage.factory

import org.datausagetracing.integration.common.usage.UsageBuilder
import org.datausagetracing.integration.common.usage.endpoint
import java.net.InetAddress
import java.util.regex.Pattern

/**
 * Processor that set's $.endpoint.service (the service that exposes the endpoint which was called)
 * based on the Hostname. It is also respected if the current runs inside the Kubernetes Pod.
 * In that case the hostname matches the pod name, and we are going to remove the suffix from the
 * replica set / stateful set away.
 *
 * @author p4skal
 */
class HostnameBasedServerUsageExtractor : UsageExtractor<UsageContext> {
    private val kubernetesEnvironmentVariable = "KUBERNETES_SERVICE_HOST"
    private val replicaSetPattern = Pattern.compile("^(.*)-[0-9a-z]{10}-[0-9a-z]{5}$")
    private val statefulSetPattern = Pattern.compile("^(.*)-\\d+$")

    override fun UsageBuilder.extract(context: UsageContext) {
        endpoint {
            val hostName = InetAddress.getLocalHost().hostName

            if (System.getenv(kubernetesEnvironmentVariable) != null) {
                val replicaSetMatcher = replicaSetPattern.matcher(hostName)
                if (replicaSetMatcher.matches()) {
                    service = replicaSetMatcher.group(1)
                    return@endpoint
                }

                val statefulSetMatcher = statefulSetPattern.matcher(hostName)
                if (statefulSetMatcher.matches()) {
                    service = statefulSetMatcher.group(1)
                }
            } else {
                service = hostName
            }
        }
    }
}