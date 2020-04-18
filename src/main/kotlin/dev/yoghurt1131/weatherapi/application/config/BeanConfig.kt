package dev.yoghurt1131.weatherapi.application.config

import dev.yoghurt1131.weatherapi.application.properties.OpenWeatherApiProperties
import dev.yoghurt1131.weatherapi.domain.input.valueobject.FiveDaysForecast
import dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.FiveDayForecastWrapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder) = builder.build()

    @Bean
    fun fiveDayForecastWrapper(apiProerties: OpenWeatherApiProperties, restTemplate: RestTemplate) =
            FiveDayForecastWrapper(apiProerties.url, apiProerties.key, restTemplate, FiveDaysForecast::class.java)
}
