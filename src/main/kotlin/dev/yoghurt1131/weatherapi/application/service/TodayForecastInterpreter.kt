package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TodayForecastInterpreter : WeatherInterpreter {

    override fun interpret(cityName: String, weatherData: List<RangedWeather>): Forecast {
        val endOfTody = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59)
        val todaysWeather: List<RangedWeather> = weatherData.filter { toDateTime(it.utcDatetime).isBefore(endOfTody) }

        // 多数決で天気を決める
        val status = todaysWeather.flatMap { it.weathers }
                            .groupBy { it.status }
                            .mapValues { it.value.count() }
                            .maxBy { it.value }?.key ?: "unknown"
        val weatherIconUrl = todaysWeather.flatMap { it.weathers }
                .find { it.status == status }!!.getWeatherIconUrl()

        val maxTemperature = todaysWeather.map { it.property }
                .map{ it.temperatureMax }.max() ?: 0.0
        val minTemperature = todaysWeather.map { it.property }
                .map{ it.temperatureMin }.min() ?: 0.0
        return Forecast(cityName, status, weatherIconUrl, maxTemperature, minTemperature)
    }

    private fun toDateTime(datetimeTxt: String): LocalDateTime {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(datetimeTxt, formatter)
    }
}