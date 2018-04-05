package com.kitchen.market.common.security.encryption.single;

import org.javatuples.Pair;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * PBKDF2算法加密
 * 该算法原理大致相当于在HASH算法基础上增加随机盐，并进行多次HASH运算，随机盐使得彩虹表的建表难度大幅增加，而多次HASH也使得建表和破解的难度都大幅增加。
 * 使用PBKDF2算法时，HASH算法一般选用sha1或者sha256，随机盐的长度一般不能少于8字节，HASH次数至少也要1000次，这样安全性才足够高。
 * 一次密码验证过程进行1000次HASH运算，对服务器来说可能只需要1ms，但对于破解者来说计算成本增加了1000倍，而至少8字节随机盐，更是把建表难度提升了N个数量级，
 * 使得大批量的破解密码几乎不可行，该算法也是美国国家标准与技术研究院推荐使用的算法。
 *
 * @date 2017-01-01
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitEncryptionPBKDF2 {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int SALT_BYTES = 24;
    public static final int HASH_BYTES = 24;
    public static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 使用随机盐值加密
     * <p>
     * 返回参数说明：
     * 1、盐值原文
     * 2、转为16进制后的盐值
     * 3、转为16进制后的密文
     *
     * @param content
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static Pair<String, String> encryptByRandomSalt(String content) {
        byte[] randomSalt = getRandomSalt();

        byte[] ciphertext = new byte[0];
        try {
            ciphertext = pbkdf2(content.toCharArray(), randomSalt, PBKDF2_ITERATIONS, HASH_BYTES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return Pair.with(toHex(randomSalt).toUpperCase(), toHex(ciphertext).toUpperCase());
    }

    /**
     * 使用指定的盐值加密
     * <p>
     * 返回参数说明：
     * 1、转为16进制后的盐值
     * 2、转为16进制后的密文
     *
     * @param content
     * @param saltHex 盐（16进制）
     * @return
     */
    public static Pair<String, String> encryptByFixSalt(String content, String saltHex) {
        byte[] bSalt = fromHex(saltHex);
        byte[] ciphertext = new byte[0];
        try {
            ciphertext = pbkdf2(content.toCharArray(), bSalt, PBKDF2_ITERATIONS, HASH_BYTES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return Pair.with(toHex(bSalt).toUpperCase(), toHex(ciphertext).toUpperCase());
    }

    private static byte[] getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        return salt;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param hex the hex string
     * @return the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }


}
