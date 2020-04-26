package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.application.service.adapter.WeatherInterpreter
import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.CityWeather
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.TemperatureData
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.WeatherDetailData
import dev.yoghurt1131.weatherapi.infrastructure.weather.CurrentWeatherWrapper
import dev.yoghurt1131.weatherapi.infrastructure.weather.FiveDayForecastWrapper
import dev.yoghurt1131.weatherapi.infrastructure.redis.CustomRedisTemplate
import dev.yoghurt1131.weatherapi.infrastructure.redis.RedisTemplateBuilder
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.mockito.MockitoAnnotations
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.concurrent.TimeUnit

object WeatherApiServiceImplTest: Spek({

    // object setup
    val currentWeatherWrapper by memoized { mockk<CurrentWeatherWrapper>() }
    val fiveDaysForecastWrapper by memoized { mockk<FiveDayForecastWrapper>() }
    val redisTemplate by memoized { mockk<CustomRedisTemplate<CityWeather>>() }
    val redisTemplateBuilder by memoized { mockk<RedisTemplateBuilder>() }
    val weatherInterpreter by memoized { mockk<WeatherInterpreter>() }

    val target by memoized { WeatherApiServiceImpl(
            currentWeatherWrapper, fiveDaysForecastWrapper, redisTemplateBuilder, weatherInterpreter) }


    // sample data
    val temperature by memoized { TemperatureData(299.7, 1010, 84, 300.1, 290.5) }
    val weather by memoized { WeatherDetailData(501, "Rain", "moderate rain", "10n", "https://xxxx") }
    val city by memoized { CityWeather("Tokyo", listOf(weather), temperature) }
    val cityName by memoized { "Tokyo" }

    describe("getCurrentWeather()") {
       beforeEachTest {
           MockitoAnnotations.initMocks(this)
           every { redisTemplateBuilder.build(CityWeather::class.java) } returns redisTemplate

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
            every { currentWeatherWrapper.execute(City.Tokyo) } returns city
            every { redisTemplate.write("Tokyo", city, 30, TimeUnit.MINUTES) } just Runs

            val expected = city.buildWeather()
            val actual = target.getCurrentWeather("Tokyo")

            assertEquals(actual.cityName, expected.cityName)
        }

    }
})