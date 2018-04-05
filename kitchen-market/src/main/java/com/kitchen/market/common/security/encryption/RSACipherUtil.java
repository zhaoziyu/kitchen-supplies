package com.kitchen.market.common.security.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-08
 */
public class RSACipherUtil {
    public static void cipherDoFinal(String srcFilePath, String targetFilePath, Cipher cipher, int maxBlock) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        File srcFile = new File(srcFilePath);
        FileInputStream in = new FileInputStream(srcFile);
        File targetFile = new File(targetFilePath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();
        OutputStream out = new FileOutputStream(targetFile);
        byte[] data = new byte[maxBlock];
        byte[] handleData;
        while (in.read(data) != -1) {
            handleData = cipher.doFinal(data);
            out.write(handleData, 0, handleData.length);
            out.flush();
        }
        out.close();
        in.close();
    }
    public static byte[] cipherDoFinal(byte[] inputBytes, Cipher cipher, int maxBlock) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = new byte[maxBlock];
        byte[] handleData;
        while (inputStream.read(data) != -1) {
            handleData = cipher.doFinal(data);
            out.write(handleData, 0, handleData.length);
            out.flush();
        }
        inputStream.close();
        out.close();

        return out.toByteArray();
    }

    /**
     * 分段计算（用于文本的加密和解密）
     * @return
     */
    public static byte[] sectionCipher(byte[] data, Cipher cipher, int maxBlock) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // 解密byte数组最大长度限制: 128
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxBlock) {
                cache = cipher.doFinal(data, offSet, maxBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxBlock;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();

        return decryptedData;
    }
}
