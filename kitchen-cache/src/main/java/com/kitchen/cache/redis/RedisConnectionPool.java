package com.kitchen.cache.redis;

import com.kitchen.cache.KitchenCacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;

/**
 * Redis连接池
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Component
class RedisConnectionPool {
    private static Logger logger = LoggerFactory.getLogger(RedisConnectionPool.class);

    private static JedisPool jedisPool;

    static {
        if (KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            logger.info("Kitchen缓存模块：Redis缓存启用，开始初始化");

            String password = KitchenCacheConfig.KITCHEN_CACHE_REDIS_PASSWORD;
            if (password == null || password.isEmpty()) {
                throw new RuntimeException(".properties中未配置Redis密码");
            }

            String ip = KitchenCacheConfig.KITCHEN_CACHE_REDIS_IP;
            int port = KitchenCacheConfig.KITCHEN_CACHE_REDIS_PORT;
            int maxTotal = KitchenCacheConfig.KITCHEN_CACHE_REDIS_MAX_TOTAL;
            int maxIdle = KitchenCacheConfig.KITCHEN_CACHE_REDIS_MAX_IDLE;
            int maxWaitMillis = KitchenCacheConfig.KITCHEN_CACHE_REDIS_MAX_WAIT_MILLIS;
            int connectionTimeout = KitchenCacheConfig.KITCHEN_CACHE_REDIS_CONNECTION_TIMEOUT;

            logger.info("Redis Host:" + ip + ":" + port);
            logger.info("Redis Client最大连接数:" + maxTotal);
            logger.info("Redis Client最大空闲数:" + maxIdle);
            logger.info("Redis Client获取Jedis超时时间:" + maxWaitMillis + "毫秒");
            logger.info("Redis Client连接超时时间:" + connectionTimeout + "毫秒");

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWaitMillis);
            config.setTestOnBorrow(true);//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

            jedisPool = new JedisPool(config, ip, port, connectionTimeout, password);
        } else {
            logger.info("Kitchen缓存模块：未启用Redis缓存，如需使用Redis缓存，请在配置文件(.properties)中配置相关属性，启用Redis缓存");
        }
    }

    /**
     * 从Jedis资源池中获取Jedis对象
     * @return
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 释放Jedis对象到Jedis资源池
     * @param jedis
     */
    public static void releaseJedis(Jedis jedis) {
        jedis.close();
    }

    @PreDestroy
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Redis连接池资源清理");
        jedisPool.close();

        super.finalize();
    }

}
