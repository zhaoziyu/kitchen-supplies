package com.kitchen.sdk.converter;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-27
 */
public class FastJsonSerializeConfig extends SerializeConfig {
    private static FastJsonSerializeConfig ourInstance = new FastJsonSerializeConfig();

    public static FastJsonSerializeConfig getInstance() {
        return ourInstance;
    }

    private FastJsonSerializeConfig() {
        this.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }
}
