package dev.yoghurt1131.weatherapi.domain.entity

enum class WeatherStatus() {
    SUNNY,
    CLOUDY,
    RAINY,
    SNOW,
    UNKNOWN;

    companion object Builder {
        fun of(value: String): WeatherStatus = when (value) {
            "Clear" -> SUNNY
            "Clouds" -> CLOUDY
            "Rain" -> RAINY
            "Snow" -> SNOW
            else -> UNKNOWN
        }
    }
}