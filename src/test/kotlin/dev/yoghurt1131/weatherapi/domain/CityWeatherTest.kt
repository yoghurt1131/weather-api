package dev.yoghurt1131.weatherapi.domain

import dev.yoghurt1131.weatherapi.infrastructure.weather.response.WeatherDetailData
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.CityWeather
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.TemperatureData
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CityWeatherTest : Spek( {

    lateinit var cityWeather: CityWeather;
    val weathers = listOf(
            WeatherDetailData(800, "Clear", "clear sky", "01n", "weather://icon.png")
    )
    val temperature = TemperatureData(281.52, 1000, 60, 283.71, 280.04)

    describe("extractWeather()") {
        context("weathers is not empty") {
            it ("returns first weather status") {
                cityWeather = CityWeather("Tokyo", weathers, temperature)
                assertEquals("Clear", cityWeather.extractWeather())
            }
        }
        context("weathers is empty") {
            it ("returns empty string") {
                cityWeather = CityWeather("Tokyo", emptyList(), temperature)
                assertEquals("", cityWeather.extractWeather())
            }
        }
    }
})