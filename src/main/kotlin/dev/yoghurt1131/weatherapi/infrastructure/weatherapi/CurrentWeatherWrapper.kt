package dev.yoghurt1131.weatherapi.infrastructure.weatherapi

import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.CityWeather
import org.springframework.web.client.RestTemplate

class CurrentWeatherWrapper(
        apiUrl: String,
        apiKey: String,
        restTemplate: RestTemplate,
        type: Class<CityWeather>
)  : CityRequestWrapper<CityWeather>(apiUrl, apiKey, restTemplate, type) {
}