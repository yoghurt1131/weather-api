package com.yoghurt1131.weatherapi.config;

import com.yoghurt1131.weatherapi.domain.City;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, City> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, City> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
        redisTemplate.setHashValueSerializer(redisTemplate.getValueSerializer());
        return redisTemplate;
    }
}
