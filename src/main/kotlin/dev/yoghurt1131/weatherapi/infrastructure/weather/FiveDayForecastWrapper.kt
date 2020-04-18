package dev.yoghurt1131.weatherapi.infrastructure.weather

import dev.yoghurt1131.weatherapi.infrastructure.weather.response.FiveDaysForecast
import org.springframework.web.client.RestTemplate

class FiveDayForecastWrapper(
        apiUrl: String,
        apiKey: String,
        restTemplate: RestTemplate,
        type: Class<FiveDaysForecast>
)  : CityRequestWrapper<FiveDaysForecast>(apiUrl, apiKey, restTemplate, type) {
}