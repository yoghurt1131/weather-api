package dev.yoghurt1131.weatherapi.application.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import kotlin.test.assertEquals

object TodayForecastInterpreterTest: Spek({
    describe("interpret()") {
        it ("returns Forecast object which decided from weathers list") {
            // data setup
            val cityName = "Tokyo"
            // read raw data
            val json = File("src/test/resources/data/forecast.json").readText(Charsets.UTF_8)

            val weathers: List<RangedWeather> = jacksonObjectMapper().readValue(json)

            // execute method
            val actual = TodayForecastInterpreter().interpret(cityName, weathers)

            // assert
            assertEquals("Tokyo", actual.cityName)
            assertEquals("Rain", actual.status)
            assertEquals("http://openweathermap.org/img/w/10n.png", actual.weatherIconUrl)
            assertEquals(299.715, actual.maxTemperature)
            assertEquals(296.17, actual.minTemperature)
        }
    }
})