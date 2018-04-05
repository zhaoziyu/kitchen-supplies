package com.kitchen.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 测试
 *
 * @date 2017-01-09
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class JedisPoolTest {
    JedisPool jedisPool = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("Kitchen缓存模块：Redis缓存初始化");
        JedisPoolConfig config = new JedisPoolConfig();

        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 1000, "XsJYvyrytaOU52Kn2Vvy");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("关闭连接池");
        jedisPool.close();
    }

    @Test()
    public void _1_setTest(){
        jedisPool.getResource().set("kitchen", "Copyright 赵梓彧");
    }

    @Test
    public void _2_getTest(){
        System.out.println("GET Foo：" + jedisPool.getResource().get("kitchen"));
    }

}