package dev.yoghurt1131.weatherapi.infrastructure.weatherapi

import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.FiveDaysForecastResponse
import org.springframework.web.client.RestTemplate

class FiveDayForecastWrapper(
        apiUrl: String,
        apiKey: String,
        restTemplate: RestTemplate,
        type: Class<FiveDaysForecastResponse>
)  : CityRequestWrapper<FiveDaysForecastResponse>(apiUrl, apiKey, restTemplate, type) {
}