package com.kitchen.cache.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis缓存操作
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RedisCacheHelper {

    public static Jedis getJedis() {
        return RedisConnectionPool.getJedis();
    }
    public static void releaseJedis(Jedis jedis) {
        RedisConnectionPool.releaseJedis(jedis);
    }

    public static class CommonHelper {
        /**
         * 设置Key过期时间，以秒为单位
         * @param key
         * @param expire
         */
        public static void expire(String key, int expire) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.expire(key, expire);
            RedisConnectionPool.releaseJedis(jedis);
        }

        /**
         * 设置Key过期时间，Unix Time
         * @param key
         * @param expireTime
         */
        public static void expireAt(String key, long expireTime) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.expireAt(key, expireTime);
            RedisConnectionPool.releaseJedis(jedis);
        }

        /**
         * 删除多个
         * @param keys
         */
        public static void deleteKeys(String... keys) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.del(keys);
            RedisConnectionPool.releaseJedis(jedis);
        }
    }

    public static class StringHelper {
        /**
         * 添加String类型的值，默认用不过期
         * @param key
         * @param value
         */
        public static void setString(String key, String value) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.set(key, value);
            RedisConnectionPool.releaseJedis(jedis);
        }

        /**
         * 添加String类型的值，并设置key的过期时间，以秒为单位
         * @param key
         * @param value
         * @param expire 多少秒后过期
         */
        public static void setString(String key, String value, int expire) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.set(key, value);
            jedis.expire(key, expire);
            RedisConnectionPool.releaseJedis(jedis);
        }

        /**
         * 添加String类型的值，并设置key的过期时间，到达指定日期时间（时间戳）后过期
         * @param key
         * @param value
         * @param expireTime 到达过期时间
         */
        public static void setString(String key, String value, long expireTime) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.set(key, value);
            jedis.expireAt(key, expireTime);
            RedisConnectionPool.releaseJedis(jedis);
        }

        public static String getString(String key) {
            Jedis jedis = RedisConnectionPool.getJedis();
            String result = jedis.get(key);
            RedisConnectionPool.releaseJedis(jedis);
            return result;
        }
    }

    public static class ObjHelper {
        /**
         * 缓存任意对象类型
         * @param key
         * @param value
         */
        public static void setObject(String key, Object value) {
            Jedis jedis = RedisConnectionPool.getJedis();
            String objectJson = JSON.toJSONString(value);
            jedis.set(key, objectJson);
            RedisConnectionPool.releaseJedis(jedis);
        }

        public static <T> T getObject(String key, Class<T> clazz) {
            T result = null;
            Jedis jedis = RedisConnectionPool.getJedis();
            try {
                String objJson = jedis.get(key);
                result = JSON.parseObject(objJson, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionPool.releaseJedis(jedis);
            }
            return result;
        }
    }

    public static class ListHelper {
        /**
         * 缓存字符串类型的数组
         * @param key
         * @param values
         */
        public static void listAdd(String key, String... values) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.lpush(key, values);
            RedisConnectionPool.releaseJedis(jedis);
        }
        public static List<String> getStringList(String key) {
            List<String> result = null;
            Jedis jedis = RedisConnectionPool.getJedis();
            try {
                if (jedis.exists(key)) {
                    result = jedis.lrange(key, 0, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionPool.releaseJedis(jedis);
            }
            return result;
        }

        /**
         * 缓存对象类型的数组
         * @param key
         * @param values
         */
        public static void listAdd(String key, Object... values) {
            Jedis jedis = RedisConnectionPool.getJedis();
            List<String> jsonObjs = new ArrayList<>();
            for (Object value : values) {
                jsonObjs.add(JSON.toJSONString(value));
            }
            jedis.lpush(key, jsonObjs.toArray(new String[jsonObjs.size()]));
            RedisConnectionPool.releaseJedis(jedis);
        }
        public static <T> List<T> getObjList(String key, Class<T> clazz) {
            List<T> result = null;
            Jedis jedis = RedisConnectionPool.getJedis();
            try {
                List<String> jsonList = jedis.lrange(key, 0, -1);
                if (jsonList != null && jsonList.size() > 0) {
                    result = new ArrayList<>();
                    for (String json : jsonList) {
                        T obj = JSON.parseObject(json, clazz);
                        result.add(obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionPool.releaseJedis(jedis);
            }
            return result;
        }
    }

    public static class MapHelper {

    }

    /**
     * 消息（发布和订阅）
     */
    public static class MessageHelper {
        /**
         * 推送（发布、发送）消息
         * @param channel
         * @param message
         */
        public static void publish(String channel, String message) {
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.publish(channel, message);
            RedisConnectionPool.releaseJedis(jedis);
        }

        /**
         * 订阅消息
         * @param channels
         */
        public static void subscribe(JedisPubSub handler, String... channels) throws Exception {
            if (channels == null) {
                throw new Exception("MessageHelper.subscribe，未填写Redis订阅的渠道");
            }
            Jedis jedis = RedisConnectionPool.getJedis();
            jedis.subscribe(handler, channels);
            RedisConnectionPool.releaseJedis(jedis);
        }
    }
}
