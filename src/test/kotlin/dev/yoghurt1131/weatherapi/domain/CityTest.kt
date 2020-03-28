package dev.yoghurt1131.weatherapi.domain

import dev.yoghurt1131.weatherapi.domain.input.valueobject.Weather
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CityTest : Spek( {

    lateinit var city: City;
    val weathers = listOf(
            Weather(800, "Clear", "clear sky", "01n", "weather://icon.png")
    )
    val temperature = Temperature(281.52, 1000, 60, 283.71, 280.04)

    describe("extractWeather()") {
        context("weathers is not empty") {
            it ("returns first weather status") {
                city = City("Tokyo", weathers, temperature)
                assertEquals("Clear", city.extractWeather())
            }
        }
        context("weathers is empty") {
            it ("returns empty string") {
                city = City("Tokyo", emptyList(), temperature)
                assertEquals("", city.extractWeather())
            }
        }
    }
})