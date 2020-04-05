package dev.yoghurt1131.weatherapi.infrastructure

import org.springframework.data.redis.connection.RedisConnectionFactory

class RedisTemplateBuilder(private val connectionFactory: RedisConnectionFactory) {

    fun <T> build(type: Class<T>): CustomRedisTemplate<T> {
        return CustomRedisTemplate(connectionFactory, type)
    }
}