package dev.yoghurt1131.weatherapi.application.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder) = builder.build()
}
