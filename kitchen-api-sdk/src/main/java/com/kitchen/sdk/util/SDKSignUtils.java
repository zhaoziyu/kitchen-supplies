package com.kitchen.sdk.util;

import com.alibaba.fastjson.JSON;
import com.kitchen.common.api.pojo.ro.BaseRequestObject;
import com.kitchen.common.api.pojo.vo.BaseJsonVo;
import com.kitchen.sdk.SDKGlobalSetting;
import com.kitchen.sdk.converter.FastJsonSerializeConfig;
import com.kitchen.sdk.sign.KitSignConstant;
import com.kitchen.sdk.sign.rsa.KitRSACheck;
import com.kitchen.sdk.sign.rsa.KitRSASign;
import com.kitchen.sdk.sign.util.ToBeSignedStringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-28
 */
public class SDKSignUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean checkSign(BaseJsonVo result) throws Exception {
        if (result.getSign() != null && !result.getSign().isEmpty()) {
            // 整理请求参数（组成待签名字符串）
            String returnJson = JSON.toJSONString(result, FastJsonSerializeConfig.getInstance());
            String returnToBeSignedString = ToBeSignedStringUtil.getToBeSignedString(returnJson);
            // 验证签名
            boolean pass = KitRSACheck.rsaCheck(returnToBeSignedString, result.getSign(), SDKGlobalSetting.getServerPublicKey(), KitSignConstant.SIGN_CHARSET_UTF8, result.getSignType());
            return pass;
        }
        return false;
    }

    public static String sign(BaseRequestObject requestObject) throws Exception {
        String json = JSON.toJSONString(requestObject);
        String toBeSignedString = ToBeSignedStringUtil.getToBeSignedString(json);
        String sign = KitRSASign.rsaSign(toBeSignedString, SDKGlobalSetting.getDeveloperPrivateKey(), KitSignConstant.SIGN_CHARSET_UTF8, KitSignConstant.SIGN_TYPE_RSA);

        return sign;
    }

    public static void initSignRequestObj(BaseRequestObject requestObject) {
        requestObject.setAppId(SDKGlobalSetting.getDeveloperAppId());
        requestObject.setSignType(KitSignConstant.SIGN_TYPE_RSA);
        String timestamp = formatter.format(new Date());
        requestObject.setTimestamp(timestamp);
    }

}
