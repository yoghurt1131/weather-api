package dev.yoghurt1131.weatherapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("openweatherapi")
class OpenWeatherApiProperties {
    lateinit var url: String
    lateinit var  key: String
}
