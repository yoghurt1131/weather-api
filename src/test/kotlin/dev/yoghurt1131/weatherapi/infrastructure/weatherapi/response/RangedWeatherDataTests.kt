package dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import java.time.LocalDateTime

object RangedWeatherDataTests: Spek({

    describe("RangedWeatherData.toRainForecast()") {
        // data setup
        val city = City.Tokyo
        val json = File("src/test/resources/data/weather.json").readText(Charsets.UTF_8)
        val weather: RangedWeatherData = jacksonObjectMapper().readValue(json)

        it("returns expected RainForecast List") {
            val actual = weather.toRainForecast(City.Tokyo)

            assertEquals(City.Tokyo, actual.city)
            assertEquals(LocalDateTime.of(2019, 7, 22, 18, 0, 0), actual.datetime)
            assertEquals(WeatherStatus.RAINY, actual.status)
        }
    }

})