package org.datausagetracing.integration.spring

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebTracingInterceptorConfigurer(private val webTracingInterceptor: WebTracingFilter) :
    WebMvcConfigurer {

}