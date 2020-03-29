package dev.yoghurt1131.weatherapi.application.config;

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.yoghurt1131.weatherapi.domain.City;
import dev.yoghurt1131.weatherapi.infrastructure.RedisTemplateBuilder
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
class RedisConfig {

    @Bean
    fun redisTemplateBuilder(connectionFactory: RedisConnectionFactory): RedisTemplateBuilder {
        return RedisTemplateBuilder(connectionFactory);
    }
}
