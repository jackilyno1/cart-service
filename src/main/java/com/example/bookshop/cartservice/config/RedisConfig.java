package com.example.bookshop.cartservice.config;

import com.example.bookshop.cartservice.model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public JedisConnectionFactory getConnectionFactory(){

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        return connectionFactory;
    }
    @Bean
    public RedisTemplate<Long, Cart> redisTemplate(){
        RedisTemplate<Long, Cart> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        return redisTemplate;
    }
}
