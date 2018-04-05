package com.kitchen.cache.local;

import com.kitchen.cache.KitchenCacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存操作类
 * 只提供最基本的操作，其它高级操作需从业务逻辑中实现
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-24
 */
@Component
public class LocalCacheHelper {
    private static Logger logger = LoggerFactory.getLogger(LocalCacheHelper.class);

    private static Map<String, Object> cacheMap = null;

    static {
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            logger.info("Kitchen缓存模块：本地Map缓存开启，开始初始化");
            cacheMap = new ConcurrentHashMap<>();
        } else {
            logger.info("Kitchen缓存模块：未开启本地Map缓存，如需使用，请在配置文件(.properties)中配置相关属性，启用本地缓存");
        }
    }

    private LocalCacheHelper() {
    }

    public static void set(String key, Object value) {
        if (cacheMap != null) {
            cacheMap.put(key, value);
        }
    }

    public static <T> T get(String key, Class<T> clazz) throws ClassCastException {
        if (cacheMap != null) {
            T obj;
            try {
                obj = (T) cacheMap.get(key);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassCastException("CacheHelperOnLocal.get():泛型中的对象类型错误");
            }
            return obj;
        }
        return null;
    }

    public static boolean isExist(String key) {
        if (cacheMap != null) {
            return cacheMap.containsKey(key);
        }
        return false;
    }

    public static void delete(String key) {
        if (cacheMap != null) {
            cacheMap.remove(key);
        }
    }
}
