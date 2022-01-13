package org.datausagetracing.integration.common.usage.factory.jackson

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

class YamlJacksonFieldParser : JacksonFieldParser(YAMLFactory())