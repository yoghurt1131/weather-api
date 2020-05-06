package dev.yoghurt1131.weatherapi.application.config

import dev.yoghurt1131.weatherapi.application.properties.OpenWeatherApiProperties
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.CityWeather
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.FiveDaysForecastResponse
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.CurrentWeatherWrapper
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.FiveDayForecastWrapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder) = builder.build()

    @Bean
    fun currentWeatherWrapper(apiProerties: OpenWeatherApiProperties, restTemplate: RestTemplate) =
            CurrentWeatherWrapper("${apiProerties.url}/weather", apiProerties.key, restTemplate, CityWeather::class.java)

    @Bean
    fun fiveDayForecastWrapper(apiProerties: OpenWeatherApiProperties, restTemplate: RestTemplate) =
            FiveDayForecastWrapper("${apiProerties.url}/forecast", apiProerties.key, restTemplate, FiveDaysForecastResponse::class.java)
}
