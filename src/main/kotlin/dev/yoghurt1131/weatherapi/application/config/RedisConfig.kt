package dev.yoghurt1131.weatherapi.application.config;

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.yoghurt1131.weatherapi.domain.City;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile("local")
@EnableCaching
@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, City> {
        var redisTemplate = RedisTemplate<String, City>()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(City::class.java)
        val om = jacksonObjectMapper()
        jackson2JsonRedisSerializer.setObjectMapper(om)
        redisTemplate.valueSerializer  = jackson2JsonRedisSerializer
        redisTemplate.hashKeySerializer = redisTemplate.keySerializer
        redisTemplate.hashValueSerializer = redisTemplate.valueSerializer
        return redisTemplate
    }
}
