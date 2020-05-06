package dev.yoghurt1131.weatherapi.application.service.adapter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.RangedWeatherData
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import java.time.LocalDateTime
import kotlin.test.assertEquals

object WeatherInterpreterImplTest: Spek({
    describe("toTodaysForecast()") {
        it ("returns Forecast object which decided from weathers list") {
            // data setup
            val city = City.Tokyo
            // read raw data
            val json = File("src/test/resources/data/forecast.json").readText(Charsets.UTF_8)

            val weathers: List<RangedWeatherData> = jacksonObjectMapper().readValue(json)

            // execute method
            val actual = WeatherInterpreterImpl().toTodaysForecast(city, weathers)

            // assert
            assertEquals(City.Tokyo, actual.city)
            assertEquals(WeatherStatus.RAINY, actual.status)
            assertEquals("http://openweathermap.org/img/w/10n.png", actual.weatherIconUrl)
            assertEquals(299.715, actual.maxTemperature)
            assertEquals(296.17, actual.minTemperature)
        }
    }

    describe("toDailyRainForecast()") {
        it ("returns expected RainForecast list") {
            // data setup
            val city = City.Tokyo
            // read raw data
            val json = File("src/test/resources/data/forecast.json").readText(Charsets.UTF_8)

            val weathers: List<RangedWeatherData> = jacksonObjectMapper().readValue(json)

            // execute method
            val actual = WeatherInterpreterImpl().toDailyRainForecast(city, weathers)

            // assert
            assertEquals(3, actual.size)
            assertEquals(City.Tokyo, actual[0].city)
            assertEquals(City.Tokyo, actual[1].city)
            assertEquals(City.Tokyo, actual[2].city)
            assertEquals(WeatherStatus.RAINY, actual[0].status)
            assertEquals(WeatherStatus.CLOUDY, actual[1].status)
            assertEquals(WeatherStatus.RAINY, actual[2].status)
            assertEquals(LocalDateTime.of(2019, 7, 22, 18, 0), actual[0].datetime)
            assertEquals(LocalDateTime.of(2019, 7, 22, 21, 0), actual[1].datetime)
            assertEquals(LocalDateTime.of(2019, 7, 27, 15, 0), actual[2].datetime)
        }
    }
})