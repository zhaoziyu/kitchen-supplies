package com.kitchen.market.common.security.encryption.single;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法加密
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitEncryptionMD5 {
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    private final static Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 将指定文本通过MD5加密算法进行加密（大写）
     * @return
     */
    public static String encrypt(String content) {
        String securityContent = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte [] digest = messageDigest.digest(content.getBytes(CHARSET));// md.digest() 该函数返回值为存放哈希值结果的byte数组
            securityContent = byteToString(digest).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return securityContent;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
}
