package dev.yoghurt1131.weatherapi.extentions

import dev.yoghurt1131.weatherapi.domain.WeatherStatus


fun String.toWeatherStatus(): WeatherStatus {
    return when(this) {
        "Clear" -> WeatherStatus.SUNNY
        "Clouds"-> WeatherStatus.CLOUDY
        "Rain"  -> WeatherStatus.RAINY
        "Snow"  -> WeatherStatus.SNOW
        else    -> WeatherStatus.UNKNOWN
    }
}
