package dev.yoghurt1131.weatherapi.domain

enum class WeatherStatus(private val value: String) {
    SUNNY("Clear"),
    CLOUDY("Clouds"),
    RAINY("Rain");

}