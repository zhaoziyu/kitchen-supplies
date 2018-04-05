package com.kitchen.sdk.sign.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kitchen.sdk.sign.KitSignConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 构建待签名字符串
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-25
 */
public class ToBeSignedStringUtil {
    public static String getToBeSignedString(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        // 去掉sign字段
        jsonObject.remove(KitSignConstant.FIELD_SIGN);
        // 去除空值，若为数组，则该字段不进行签名
        checkObjectJson(jsonObject);

        String toBeSignedString = JSON.toJSONString(jsonObject, SerializerFeature.MapSortField);

        return toBeSignedString;
    }

    private static void checkObjectJson(JSONObject jsonObject) {
        Set<String> keySet = jsonObject.keySet();
        List<String> toDelete = new ArrayList<>();
        for (String key : keySet) {
            if (jsonObject.get(key) == null) {
                toDelete.add(key);
                continue;
            }
            if (jsonObject.get(key) instanceof JSONObject) {
                checkObjectJson((JSONObject) jsonObject.get(key));
            } else if (jsonObject.get(key) instanceof JSONArray) {
                toDelete.add(key);
            } else if (jsonObject.get(key) instanceof String) {
                if (StringUtils.isEmpty((String) jsonObject.get(key))) {
                    toDelete.add(key);
                }
            }
        }
        // 删除
        for (String key : toDelete) {
            jsonObject.remove(key);
        }
    }
}
