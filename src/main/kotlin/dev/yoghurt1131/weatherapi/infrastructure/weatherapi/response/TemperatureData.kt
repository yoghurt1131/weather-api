package dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response

data class TemperatureData(
        val temp: Double,
        val pressure: Int,
        val humidity: Int,
        val tempMax: Double,
        val tempMin: Double
)

