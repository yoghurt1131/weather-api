package dev.yoghurt1131.weatherapi.application.service.adapter

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.Forecast
import dev.yoghurt1131.weatherapi.extentions.toWeatherStatus
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.RangedWeatherData
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class WeatherInterpreterImpl : WeatherInterpreter {
    override fun toTodaysForecast(city: City, weatherData: List<RangedWeatherData>): Forecast {
        val endOfTody = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59)
        val todaysWeather: List<RangedWeatherData> = weatherData.filter { toDateTime(it.utcDatetime).isBefore(endOfTody) }

        // 多数決で天気を決める
        val status = todaysWeather.flatMap { it.weatherDetailData }
                            .groupBy { it.status }
                            .mapValues { it.value.count() }
                            .maxBy { it.value }?.key ?: "unknown"
        val weatherIconUrl = todaysWeather.flatMap { it.weatherDetailData }
                .find { it.status == status }!!.getWeatherIconUrl()

        val maxTemperature = todaysWeather.map { it.property }
                .map { it.temperatureMax }.max() ?: 0.0
        val minTemperature = todaysWeather.map { it.property }
                .map { it.temperatureMin }.min() ?: 0.0
        return Forecast(city, status.toWeatherStatus(), weatherIconUrl, maxTemperature, minTemperature)
    }


    override fun toDailyForecast(city: City): List<Forecast> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun toDateTime(datetimeTxt: String): LocalDateTime {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(datetimeTxt, formatter)
    }
}
