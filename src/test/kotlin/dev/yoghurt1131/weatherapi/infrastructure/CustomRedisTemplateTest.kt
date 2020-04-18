package dev.yoghurt1131.weatherapi.infrastructure

import dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response.CityWeather
import dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response.Temperature
import dev.yoghurt1131.weatherapi.infrastructure.redis.CustomRedisTemplate
import io.mockk.*
import org.mockito.MockitoAnnotations
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.data.redis.RedisConnectionFailureException
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNull


object CustomRedisTemplateTest: Spek( {

    // object setup
    val connectionFactory by memoized {  mockk<RedisConnectionFactory>() }
    val valueOperations by memoized { mockk<ValueOperations<String, CityWeather>>() }
    val type by memoized { CityWeather::class.java }
    val target by memoized { spyk(CustomRedisTemplate(connectionFactory, type)) }

    // data setup
    val cityName by memoized { "Tokyo" }

    val city by memoized { CityWeather("Tokyo", emptyList(), mockk<Temperature>()) }

    describe(".read()") {
        beforeEachTest {
            MockitoAnnotations.initMocks(this)
        }

        it("returns value if present") {
            every { valueOperations.get(cityName) } returns city
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertEquals(city, actual);
        }

        it("returns null when value is not in Cache.") {
            every { valueOperations.get(cityName) } returns null
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertNull(actual)
        }

        it("returns null when exception has occurred") {
            every { valueOperations.get(cityName) } throws RuntimeException("DUMMY")
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertNull(actual)
        }
    }

    describe(".write()") {
        beforeEachTest {
            MockitoAnnotations.initMocks(this)
        }

        it("stores key and value.") {
            every { valueOperations.set(cityName, city) } just Runs
            every { target.expire(cityName, 1000, TimeUnit.MILLISECONDS) } returns true
            every { target.opsForValue() } returns valueOperations

            target.write(cityName, city, 1000, TimeUnit.MILLISECONDS)
        }

        it("do nothing when exception occurred.") {
            every { valueOperations.set(cityName, city) } throws RedisConnectionFailureException("DUMMY")
            every { target.expire(cityName, 1000, TimeUnit.MILLISECONDS) } returns true
            every { target.opsForValue() } returns valueOperations

            target.write(cityName, city, 1000, TimeUnit.MILLISECONDS)
        }

    }

})