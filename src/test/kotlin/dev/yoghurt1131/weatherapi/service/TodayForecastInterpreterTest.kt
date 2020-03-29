package dev.yoghurt1131.weatherapi.service

import dev.yoghurt1131.weatherapi.application.service.TodayForecastInterpreter
import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather
import dev.yoghurt1131.weatherapi.domain.input.valueobject.Weather
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TodayForecastInterpreterTest: Spek({
    describe("interpret()") {
        it ("returns Forecast object which decided from weathers list") {
            // data setup
            val cityName = "Tokyo"
            val weathers = listOf<RangedWeather>(
                    // RangedWeather()
            )

            // execute method
            val actual = TodayForecastInterpreter().interpret(cityName, weathers)
        }
    }
})