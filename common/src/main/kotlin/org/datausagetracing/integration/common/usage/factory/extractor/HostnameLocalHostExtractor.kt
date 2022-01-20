package org.datausagetracing.integration.common.usage.factory.extractor

import org.datausagetracing.integration.common.usage.factory.UsageContext
import java.net.InetAddress
import java.util.regex.Pattern

/**
 * Processor that extracts the host (used e.g. for $.endpoint.host or $.initiator.host)
 * based on the Hostname. It is also respected if the current runs inside the Kubernetes Pod.
 * In that case the hostname matches the pod name, and we are going to remove the suffix from the
 * replica set / stateful set away.
 *
 * @author p4skal
 */
class HostnameLocalHostExtractor : HostExtractor<UsageContext> {
    private val kubernetesEnvironmentVariable = "KUBERNETES_SERVICE_HOST"
    private val replicaSetPattern = Pattern.compile("^(.*)-[0-9a-z]{10}-[0-9a-z]{5}$")
    private val statefulSetPattern = Pattern.compile("^(.*)-\\d+$")

    override fun extractHost(context: UsageContext): String {
        val hostName = InetAddress.getLocalHost().hostName

        if (System.getenv(kubernetesEnvironmentVariable) != null) {
            val replicaSetMatcher = replicaSetPattern.matcher(hostName)
            if (replicaSetMatcher.matches()) {
                return replicaSetMatcher.group(1)
            }

            val statefulSetMatcher = statefulSetPattern.matcher(hostName)
            if (statefulSetMatcher.matches()) {
                return statefulSetMatcher.group(1)
            }

        }
        return hostName
    }
}