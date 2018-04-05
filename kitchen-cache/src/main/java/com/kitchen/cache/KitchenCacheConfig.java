package com.kitchen.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-24
 */
@Configuration
public class KitchenCacheConfig {
    // 本地缓存配置
    public static Boolean KITCHEN_CACHE_LOCAL_OPEN;

    // Redis缓存配置
    public static Boolean KITCHEN_CACHE_REDIS_OPEN;
    public static String KITCHEN_CACHE_REDIS_IP;
    public static Integer KITCHEN_CACHE_REDIS_PORT;
    public static Integer KITCHEN_CACHE_REDIS_MAX_TOTAL;
    public static Integer KITCHEN_CACHE_REDIS_MAX_IDLE;
    public static Integer KITCHEN_CACHE_REDIS_MAX_WAIT_MILLIS;
    public static Integer KITCHEN_CACHE_REDIS_CONNECTION_TIMEOUT;
    public static String KITCHEN_CACHE_REDIS_PASSWORD;

    @Value("${kitchen.cache.local.open:true}")
    public void setKitchenCacheLocalOpen(Boolean kitchenCacheLocalOpen) {
        KITCHEN_CACHE_LOCAL_OPEN = kitchenCacheLocalOpen;
    }

    @Value("${kitchen.cache.redis.open:false}")
    public void setKitchenCacheRedisOpen(Boolean kitchenCacheRedisOpen) {
        KITCHEN_CACHE_REDIS_OPEN = kitchenCacheRedisOpen;
    }
    @Value("${kitchen.cache.redis.ip:127.0.0.1}")
    public void setKitchenCacheRedisIp(String kitchenCacheRedisIp) {
        KITCHEN_CACHE_REDIS_IP = kitchenCacheRedisIp;
    }
    @Value("${kitchen.cache.redis.port:6379}")
    public void setKitchenCacheRedisPort(Integer kitchenCacheRedisPort) {
        KITCHEN_CACHE_REDIS_PORT = kitchenCacheRedisPort;
    }
    @Value("${kitchen.cache.redis.max-total:1024}")
    public void setKitchenCacheRedisMaxTotal(Integer kitchenCacheRedisMaxTotal) {
        KITCHEN_CACHE_REDIS_MAX_TOTAL = kitchenCacheRedisMaxTotal;
    }
    @Value("${kitchen.cache.redis.max-idle:256}")
    public void setKitchenCacheRedisMaxIdle(Integer kitchenCacheRedisMaxIdle) {
        KITCHEN_CACHE_REDIS_MAX_IDLE = kitchenCacheRedisMaxIdle;
    }
    @Value("${kitchen.cache.redis.max-wait-millis:5000}")
    public void setKitchenCacheRedisMaxWaitMillis(Integer kitchenCacheRedisMaxWaitMillis) {
        KITCHEN_CACHE_REDIS_MAX_WAIT_MILLIS = kitchenCacheRedisMaxWaitMillis;
    }
    @Value("${kitchen.cache.redis.connection-timeout:5000}")
    public void setKitchenCacheRedisConnectionTimeout(Integer kitchenCacheRedisConnectionTimeout) {
        KITCHEN_CACHE_REDIS_CONNECTION_TIMEOUT = kitchenCacheRedisConnectionTimeout;
    }
    @Value("${kitchen.cache.redis.password:}")
    public void setKitchenCacheRedisPassword(String kitchenCacheRedisPassword) {
        KITCHEN_CACHE_REDIS_PASSWORD = kitchenCacheRedisPassword;
    }

}
