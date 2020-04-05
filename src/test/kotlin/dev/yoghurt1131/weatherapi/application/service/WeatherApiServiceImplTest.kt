package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.Temperature
import dev.yoghurt1131.weatherapi.domain.input.valueobject.Weather
import dev.yoghurt1131.weatherapi.infrastructure.CustomRedisTemplate
import dev.yoghurt1131.weatherapi.infrastructure.RedisTemplateBuilder
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.mockito.MockitoAnnotations
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

object WeatherApiServiceImplTest: Spek({

    // object setup
    val restTemplate by memoized { mockk<RestTemplate>() }
    val redisTemplate by memoized { mockk<CustomRedisTemplate<City>>() }
    val redisTemplateBuilder by memoized { mockk<RedisTemplateBuilder>() }
    val weatherInterpreter by memoized { mockk<WeatherInterpreter>() }

    val target by memoized { WeatherApiServiceImpl(restTemplate, redisTemplateBuilder, weatherInterpreter) }


    // sample data
    val temperature by memoized { Temperature(299.7, 1010, 84, 300.1, 290.5) }
    val weather by memoized { Weather(501, "Rain", "moderate rain", "10n", "https://xxxx") }
    val city by memoized { City("Tokyo", listOf(weather),  temperature) }
    val cityName by memoized { "Tokyo" }

    describe("getCurrentWeather()") {
       beforeEachTest {
           MockitoAnnotations.initMocks(this)
           every { redisTemplateBuilder.build(City::class.java) } returns redisTemplate

           target.openWeatherApiUrl = "https://dummy"
           target.apiKey = "apiKey"
       }

        it ("returns city weather if cache exists.") {
            every { redisTemplate.read(cityName) } returns city

            val expected = city.buildWeather()
            val actual = target.getCurrentWeather(cityName)

            assertEquals(actual.cityName, expected.cityName)
            assertEquals(actual.kelvin, expected.kelvin)
            assertEquals(actual.status, expected.status)
            assertEquals(actual.temperature, expected.temperature)
            assertEquals(actual.getWeatherIconUrl(), expected.getWeatherIconUrl())
        }

        it ("returns city weather by calling api when cache not exists.") {
            every { redisTemplate.read(cityName) } returns null
            val responseEntity = ResponseEntity<City>(city, HttpStatus.OK)
            every { restTemplate.getForEntity("https://dummy/weather?q=Tokyo&APPID=apiKey", City::class.java) } returns responseEntity
            every { redisTemplate.write("Tokyo", responseEntity.body, 30, TimeUnit.MINUTES) } just Runs

            val expected = city.buildWeather()
            val actual = target.getCurrentWeather("Tokyo")

            assertEquals(actual.cityName, expected.cityName)
        }

    }
})