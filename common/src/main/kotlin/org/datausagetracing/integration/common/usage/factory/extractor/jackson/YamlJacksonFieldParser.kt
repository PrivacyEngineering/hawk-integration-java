package org.datausagetracing.integration.common.usage.factory.extractor.jackson

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

class YamlJacksonFieldParser : JacksonFieldParser(YAMLFactory()) {
    override val name = "yaml"
}