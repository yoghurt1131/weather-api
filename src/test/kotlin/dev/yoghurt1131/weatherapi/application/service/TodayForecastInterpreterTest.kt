package dev.yoghurt1131.weatherapi.application.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.yoghurt1131.weatherapi.application.service.adapter.WeatherInterpreterImpl
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.RangedWeatherData
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import kotlin.test.assertEquals

object TodayForecastInterpreterTest: Spek({
    describe("interpret()") {
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
})