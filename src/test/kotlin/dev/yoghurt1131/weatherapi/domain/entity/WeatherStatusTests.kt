package dev.yoghurt1131.weatherapi.domain.entity

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WeatherStatusTests: Spek({
    describe("Builder.of()") {
        it("returns SUNNY") {
            assertEquals(WeatherStatus.SUNNY, WeatherStatus.of("Clear"))
        }
        it("returns CLOUDY") {
            assertEquals(WeatherStatus.CLOUDY, WeatherStatus.of("Clouds"))
        }
        it("returns RAINY") {
            assertEquals(WeatherStatus.RAINY, WeatherStatus.of("Rain"))
        }
        it("returns SNOW") {
            assertEquals(WeatherStatus.SNOW, WeatherStatus.of("Snow"))
        }
        it("returns UNKNOWN") {
            assertEquals(WeatherStatus.UNKNOWN, WeatherStatus.of("XXXXX"))
        }
    }

})