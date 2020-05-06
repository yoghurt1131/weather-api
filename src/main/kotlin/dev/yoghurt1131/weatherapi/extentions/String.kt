package dev.yoghurt1131.weatherapi.extentions

import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun String.toWeatherStatus(): WeatherStatus {
    return when(this) {
        "Clear" -> WeatherStatus.SUNNY
        "Clouds"-> WeatherStatus.CLOUDY
        "Rain"  -> WeatherStatus.RAINY
        "Snow"  -> WeatherStatus.SNOW
        else    -> WeatherStatus.UNKNOWN
    }
}

fun String.toDateTime(): LocalDateTime {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(this, formatter)
}
