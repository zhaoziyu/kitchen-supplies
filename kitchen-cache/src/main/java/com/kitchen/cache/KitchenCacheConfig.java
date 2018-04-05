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
    public static Boolean KITCHEN_CACHE_LOCAL_OPEN = true;

    // Redis缓存配置
    public static Boolean KITCHEN_CACHE_REDIS_OPEN = false;
    public static String KITCHEN_CACHE_REDIS_IP = "127.0.0.1";
    public static Integer KITCHEN_CACHE_REDIS_PORT = 6379;
    public static Integer KITCHEN_CACHE_REDIS_MAX_TOTAL = 1024;
    public static Integer KITCHEN_CACHE_REDIS_MAX_IDLE = 256;
    public static Integer KITCHEN_CACHE_REDIS_MAX_WAIT_MILLIS = 5000;
    public static Integer KITCHEN_CACHE_REDIS_CONNECTION_TIMEOUT = 5000;
    public static String KITCHEN_CACHE_REDIS_PASSWORD = "XsJYvyrytaOU52Kn2Vvy";

    @Value("${kitchen.cache.local.open}")
    public void setKitchenCacheLocalOpen(Boolean kitchenCacheLocalOpen) {
        KITCHEN_CACHE_LOCAL_OPEN = kitchenCacheLocalOpen;
    }

    @Value("${kitchen.cache.redis.open}")
    public void setKitchenCacheRedisOpen(Boolean kitchenCacheRedisOpen) {
        KITCHEN_CACHE_REDIS_OPEN = kitchenCacheRedisOpen;
    }
    @Value("${kitchen.cache.redis.ip}")
    public void setKitchenCacheRedisIp(String kitchenCacheRedisIp) {
        KITCHEN_CACHE_REDIS_IP = kitchenCacheRedisIp;
    }
    @Value("${kitchen.cache.redis.port}")
    public void setKitchenCacheRedisPort(Integer kitchenCacheRedisPort) {
        KITCHEN_CACHE_REDIS_PORT = kitchenCacheRedisPort;
    }
    @Value("${kitchen.cache.redis.max-total}")
    public void setKitchenCacheRedisMaxTotal(Integer kitchenCacheRedisMaxTotal) {
        KITCHEN_CACHE_REDIS_MAX_TOTAL = kitchenCacheRedisMaxTotal;
    }
    @Value("${kitchen.cache.redis.max-idle}")
    public void setKitchenCacheRedisMaxIdle(Integer kitchenCacheRedisMaxIdle) {
        KITCHEN_CACHE_REDIS_MAX_IDLE = kitchenCacheRedisMaxIdle;
    }
    @Value("${kitchen.cache.redis.max-wait-millis}")
    public void setKitchenCacheRedisMaxWaitMillis(Integer kitchenCacheRedisMaxWaitMillis) {
        KITCHEN_CACHE_REDIS_MAX_WAIT_MILLIS = kitchenCacheRedisMaxWaitMillis;
    }
    @Value("${kitchen.cache.redis.connection-timeout}")
    public void setKitchenCacheRedisConnectionTimeout(Integer kitchenCacheRedisConnectionTimeout) {
        KITCHEN_CACHE_REDIS_CONNECTION_TIMEOUT = kitchenCacheRedisConnectionTimeout;
    }
    @Value("${kitchen.cache.redis.password}")
    public void setKitchenCacheRedisPassword(String kitchenCacheRedisPassword) {
        KITCHEN_CACHE_REDIS_PASSWORD = kitchenCacheRedisPassword;
    }

}
