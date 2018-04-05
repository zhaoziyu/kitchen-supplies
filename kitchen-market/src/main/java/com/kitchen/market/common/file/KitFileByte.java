package com.kitchen.market.common.file;

import java.io.*;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitFileByte {
    private static final int CACHE_SIZE = 2048;

    public static byte[] fileToByte(String filePath) throws Exception {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileToByte(fileInputStream);
    }

    public static byte[] fileToByte(FileInputStream inputStream) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(CACHE_SIZE);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = inputStream.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        inputStream.close();
        byte[] data = out.toByteArray();

        return data;
    }


    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
}
