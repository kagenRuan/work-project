package com.ruan.yuanyuan.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerPing;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.time.Duration;

/**
 * @ClassName: RedisCacheConfig
 * @author: ruanyuanyuan
 * @date: 2020/7/5 15:24
 * @version: 1.0
 * @description: Redis缓存配置类
 **/
@Configuration
public class RedisCacheConfig extends AbstractLoadBalancerPing {


    @Bean
    public IPing iPing(){
        return new NIWSDiscoveryPing();
    }

    @Override
    public boolean isAlive(Server server) {
        return super.isAlive(server);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }
}
