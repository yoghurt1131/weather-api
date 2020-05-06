package dev.yoghurt1131.weatherapi.extentions

import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object StringTest : Spek({

    describe("toWeatherStatus") {
        it ("return Expected Enum") {
            assertEquals(WeatherStatus.SUNNY, "Clear".toWeatherStatus())
            assertEquals(WeatherStatus.CLOUDY, "Clouds".toWeatherStatus())
            assertEquals(WeatherStatus.RAINY, "Rain".toWeatherStatus())
            assertEquals(WeatherStatus.SNOW, "Snow".toWeatherStatus())
            assertEquals(WeatherStatus.UNKNOWN, "ccclear".toWeatherStatus())
        }
    }
})