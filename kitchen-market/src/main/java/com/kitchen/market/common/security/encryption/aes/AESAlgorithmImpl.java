package com.kitchen.market.common.security.encryption.aes;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-08
 */
class AESAlgorithmImpl {
    public static void encryptFile(String srcFile, String goalFile, String key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        operateFile(srcFile, goalFile, Cipher.ENCRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static void decryptFile(String srcFile, String goalFile, String key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        operateFile(srcFile, goalFile, Cipher.DECRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    public static byte[] encryptFile(byte[] data, String key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        return operateFile(data, Cipher.ENCRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static byte[] decryptFile(byte[] data, String key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        return operateFile(data, Cipher.DECRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    public static byte[] encryptText(byte[] data, String key, String iv, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) {
        return operateText(Cipher.ENCRYPT_MODE, data, key, iv, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static byte[] decryptText(byte[] data, String key, String iv, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) {
        return operateText(Cipher.DECRYPT_MODE, data, key, iv, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    private static byte[] operateText(int mode, byte[] data, String key, String iv, String algorithm, String workingMode, String padding) {
        try {
            // 创建加密工具
            Cipher cipher = createCipher(mode, key, iv, algorithm, workingMode, padding);
            // 执行加密
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void operateFile(String srcFile, String goalFile, int mode, String key, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        operateFile(srcFile, goalFile, mode, key, null, algorithm, workingMode, padding);
    }
    private static void operateFile(String srcFile, String goalFile, int mode, String key, String iv, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 创建加密工具
        Cipher cipher = createCipher(mode, key, iv, algorithm, workingMode, padding);
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(goalFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }


    private static byte[] operateFile(byte[] data, int mode, String key, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        return operateFile(data, mode, key, null, algorithm, workingMode, padding);
    }
    private static byte[] operateFile(byte[] data, int mode, String key, String iv, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建加密工具
        Cipher cipher = createCipher(mode, key, iv, algorithm, workingMode, padding);
        try {

            crypt(inputStream, outputStream, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return outputStream.toByteArray();
    }

    /**
     * 创建加密器
     * @param mode
     * @param key
     * @param algorithm
     * @param workingMode
     * @param padding
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    private static Cipher createCipher(int mode, String key, String customIv, String algorithm, String workingMode, String padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        String fullAlg = algorithm + "/" + workingMode + "/" + padding;
        Cipher cipher = Cipher.getInstance(fullAlg);

        key = getInvalidKey(key, cipher.getBlockSize());// 加密密钥容错
        SecretKey secretKey = getSecretKeyBySpec(algorithm, key.getBytes());

        if (!AESWorkingType.ECB.getName().equalsIgnoreCase(workingMode)) {
            // 非ECB工作模式都需要传入向量
            if (customIv != null) {
                customIv = getInvalidIv(customIv, cipher.getBlockSize());// 加密向量容错
            } else {
                // 使用默认向量
                customIv = getInvalidIv("", cipher.getBlockSize());
            }
            IvParameterSpec ivSpec = new IvParameterSpec(customIv.getBytes());
            cipher.init(mode, secretKey, ivSpec);
        } else {
            // ECB工作模式无需向量
            cipher.init(mode, secretKey);
        }
        return cipher;
    }
    private static String getInvalidIv(String iv, int blockSize) {
        return getInvalidKey(iv, blockSize);
    }
    private static String getInvalidKey(String key, int blockSize) {
        if (key.length() == blockSize) {
            return key;
        } else if (key.length() > blockSize) {
            // 剪切
            return key.substring(0, blockSize);
        } else {
            // 补零
            return key + String.format("%1$0"+(blockSize-key.length())+"d",0);
        }
    }


    /**
     * 生成密钥
     * 第一种，Factory
     * 需使用规范的密钥——byte[] invalidKey = getInvalidKey(key, algorithm);处理密钥规范
     */
    private static SecretKey getSecretKeyByFactory(String algorithm, byte[] keyBytes) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec keySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        return secretKey;
    }
    /**
     * 生成密钥
     * 第二种，Generator
     */
    private static SecretKey getSecretKeyByGenerator(String algorithm, byte[] keyBytes) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        int keySize = 0;
        if (AESAlgorithmType.AES.getName().equals(algorithm)) {
            keySize = 128;// AES算法需要128位密钥
        }

        SecureRandom random = SecureRandom.getInstance(AESAlgorithmType.SHA1PRNG.getName());
        random.setSeed(keyBytes);

        keyGenerator.init(keySize, random);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }
    /**
     * 生成密钥
     * 第三种，SecretKeySpec
     * 需使用规范的密钥——byte[] invalidKey = getInvalidKey(key, algorithm);处理密钥规范
     */
    private static SecretKey getSecretKeyBySpec(String algorithm, byte[] keyBytes) throws NoSuchAlgorithmException {
        //SecretKeySpec类同时实现了Key和KeySpec接口
        SecretKey secretKey = new SecretKeySpec(keyBytes, algorithm);
        return secretKey;
    }

    /**
     * 根据filePath创建相应的目录
     *
     * @param filePath 要创建的文件路经
     * @return file        文件
     * @throws IOException
     */
    private static File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }
    /**
     * 加密解密流
     *
     * @param in     加密解密前的流
     * @param out    加密解密后的流
     * @param cipher 加密解密
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }
}
