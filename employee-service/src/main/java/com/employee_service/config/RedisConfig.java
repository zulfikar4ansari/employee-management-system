package com.employee_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis configuration for employee-service.
 * Purpose: Provide redis client beans to store/retrieve employee profile cache.
 */
@Configuration
public class RedisConfig {

    /**
     * Creates Redis connection factory.
     * Uses spring.redis.* properties from application.yml by default.
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * StringRedisTemplate helps store key/value as strings.
     * We store JSON data as String.
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}
