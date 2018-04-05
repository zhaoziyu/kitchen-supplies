package com.kitchen.market.common.security.sign.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public class KitSignFileByMD5 {
    private static int BUFFER_SIZE = 8192;
    private static String ALGORITHM_MD5 = "MD5";

    /**
     * 根据文件路径获取指定文件的MD5
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static String getMD5(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        return getMD5(file);
    }
    public static String getMD5(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return generateMD5(fileInputStream);
    }

    /**
     * 获取文件的MD5
     *
     * @param fileInputStream
     * @return
     */
    private static String generateMD5(FileInputStream fileInputStream) {
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM_MD5);

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }

            BigInteger bi = new BigInteger(1, md5.digest());
            result = bi.toString(16);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toUpperCase();
        }
    }
}
