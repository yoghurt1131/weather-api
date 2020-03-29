package dev.yoghurt1131.weatherapi.application.config

import dev.yoghurt1131.weatherapi.domain.City
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.clients.jedis.JedisPoolConfig
import java.net.URI

@Profile("heroku")
@Configuration
class RedisConfigHeroku {

    @Bean
    fun jedisPoolConfig() : JedisPoolConfig{
       var jedisPoolConfig = JedisPoolConfig()
        jedisPoolConfig.maxTotal = 10
        jedisPoolConfig.maxIdle = 5
        jedisPoolConfig.minIdle = 1
        jedisPoolConfig.testOnBorrow = true
        jedisPoolConfig.testOnReturn = true
        jedisPoolConfig.testWhileIdle = true
        return jedisPoolConfig
    }

    @Bean
    fun jedisConnectionFactory(jedisPoolConfig: JedisPoolConfig): JedisConnectionFactory {
        val redisUri = URI(System.getenv("REDIS_URL"))
        var hostConfig = RedisStandaloneConfiguration()
        hostConfig.port = redisUri.port
        hostConfig.hostName = redisUri.host
        hostConfig.password = RedisPassword.of(getPassword(redisUri))

        val clientConfig = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(jedisPoolConfig)
                .build();
        return JedisConnectionFactory(hostConfig, clientConfig)
    }

    @Bean
    fun redisTemplat(jedisConnectionFactory: JedisConnectionFactory): RedisTemplate<String, City> {
        var redisTemplate = RedisTemplate<String, City>()
        redisTemplate.connectionFactory = jedisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.hashKeySerializer = redisTemplate.keySerializer
        redisTemplate.hashValueSerializer = redisTemplate.hashValueSerializer
        return redisTemplate
    }

    private fun getPassword(uri: URI) = uri.userInfo.split(":")[1]
}