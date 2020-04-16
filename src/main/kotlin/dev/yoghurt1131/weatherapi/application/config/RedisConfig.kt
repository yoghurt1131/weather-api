package dev.yoghurt1131.weatherapi.application.config

import dev.yoghurt1131.weatherapi.infrastructure.RedisTemplateBuilder
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory

@EnableCaching
@Configuration
class RedisConfig {

    @Bean
    fun redisTemplateBuilder(connectionFactory: RedisConnectionFactory): RedisTemplateBuilder {
        return RedisTemplateBuilder(connectionFactory)
    }
}
