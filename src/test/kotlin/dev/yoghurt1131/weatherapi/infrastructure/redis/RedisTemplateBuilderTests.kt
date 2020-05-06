package dev.yoghurt1131.weatherapi.infrastructure.redis

import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.CityWeather
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.data.redis.connection.RedisConnectionFactory
import kotlin.test.assertEquals

object RedisTemplateBuilderTests : Spek({

    val connectionFactory by memoized { mockk<RedisConnectionFactory>() }

    describe("build()") {
        it ("returns CustomizedRedisTemplate using connectionFactory and Expected Type") {
            val redisTemplateBuilder = RedisTemplateBuilder(connectionFactory)
            val actual = redisTemplateBuilder.build(CityWeather::class.java)
            assertEquals(CustomRedisTemplate<CityWeather>::javaClass.name, actual::javaClass.name)
        }
    }

})