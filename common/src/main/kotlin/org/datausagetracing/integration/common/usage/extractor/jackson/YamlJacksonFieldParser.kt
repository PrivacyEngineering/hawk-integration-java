package org.datausagetracing.integration.common.usage.extractor.jackson

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

class YamlJacksonFieldParser : JacksonFieldParser(YAMLFactory())