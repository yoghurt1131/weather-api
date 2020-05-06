package dev.yoghurt1131.weatherapi.infrastructure.weatherapi

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.CityWeather
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.FiveDaysForecastResponse
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.TemperatureData
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object CityRequestWrapperTests : Spek( {

    val apiUrl by memoized { "https://weather-api" }
    val apiKey by memoized { "key" }
    val restTemplate by memoized { mockk<RestTemplate>() }

    val currentWeatherWrapper by memoized { CurrentWeatherWrapper(apiUrl, apiKey, restTemplate, CityWeather::class.java) }
    val fiveDayForecastWrapper by memoized { FiveDayForecastWrapper(apiUrl, apiKey, restTemplate, FiveDaysForecastResponse::class.java) }

    val cityWeatherResponse by memoized { CityWeather("Tokyo", emptyList(), mockk<TemperatureData>()) }
    val forecastResponse by memoized { FiveDaysForecastResponse(emptyList())}

    describe("CurrentWeatherWrapper.execute()") {
        context("execute successfully") {
            it("returns expected response body") {
                val responseEntity = ResponseEntity(cityWeatherResponse, HttpStatus.OK)
                every { restTemplate.getForEntity("https://weather-api?q=Tokyo&APPID=key", CityWeather::class.java) } returns responseEntity
                val actual = currentWeatherWrapper.execute(City.Tokyo)

                assertEquals(cityWeatherResponse, actual)
            }
        }
        context("execute unsuccessfully") {
            it("throws ApiCallException when api returns 500 error") {
                every { restTemplate.getForEntity("https://weather-api?q=Tokyo&APPID=key", CityWeather::class.java) } throws HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR) as Throwable
                assertFailsWith(ApiCallException::class) {
                    val actual = currentWeatherWrapper.execute(City.Tokyo)
                }
            }
        }
    }

    describe("FiveDayForecastWrapper.execute()") {
        it("returns expected response body") {
            val responseEntity = ResponseEntity(forecastResponse, HttpStatus.OK)
            every { restTemplate.getForEntity("https://weather-api?q=Tokyo&APPID=key", FiveDaysForecastResponse::class.java) } returns responseEntity
            val actual = fiveDayForecastWrapper.execute(City.Tokyo)

            assertEquals(forecastResponse, actual)
        }
    }

})