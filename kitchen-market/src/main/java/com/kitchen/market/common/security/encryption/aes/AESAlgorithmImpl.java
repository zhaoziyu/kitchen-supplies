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
    public static void encryptFile(String srcFile, String goalFile, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        operateFile(srcFile, goalFile, Cipher.ENCRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static void decryptFile(String srcFile, String goalFile, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        operateFile(srcFile, goalFile, Cipher.DECRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    public static byte[] encryptFile(byte[] data, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        return operateFile(data, Cipher.ENCRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static byte[] decryptFile(byte[] data, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) throws GeneralSecurityException, IOException {
        return operateFile(data, Cipher.DECRYPT_MODE, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    public static byte[] encryptText(byte[] data, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) {
        return operateText(Cipher.ENCRYPT_MODE, data, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }
    public static byte[] decryptText(byte[] data, byte[] key, AESAlgorithmType algorithm, AESWorkingType workingMode, AESPaddingType padding) {
        return operateText(Cipher.DECRYPT_MODE, data, key, algorithm.getName(), workingMode.getName(), padding.getName());
    }

    private static byte[] operateText(int mode, byte[] data, byte[] key, String algorithm, String workingMode, String padding) {
        try {
            // 创建加密工具
            Cipher cipher = createCipher(mode, key, algorithm, workingMode, padding);
            // 执行加密
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void operateFile(String srcFile, String goalFile, int mode, byte[] key, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 创建加密工具
        Cipher cipher = createCipher(mode, key, algorithm, workingMode, padding);
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
    private static byte[] operateFile(byte[] data, int mode, byte[] key, String algorithm, String workingMode, String padding) throws GeneralSecurityException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建加密工具
        Cipher cipher = createCipher(mode, key, algorithm, workingMode, padding);
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
    private static Cipher createCipher(int mode, byte[] key, String algorithm, String workingMode, String padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        String fullAlg = algorithm + "/" + workingMode + "/" + padding;

        SecretKey secretKey = getSecretKeyByGenerator(algorithm, key);

        Cipher cipher = Cipher.getInstance(fullAlg);
        if (!AESWorkingType.ECB.getName().equalsIgnoreCase(workingMode)) {
            // 非ECB工作模式都需要传入向量
            byte[] iv = initIv(algorithm, workingMode, padding);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(mode, secretKey, ivSpec);
        } else {
            // ECB工作模式无需向量
            cipher.init(mode, secretKey);
        }
        return cipher;
    }

    /**
     * 处理密钥规范
     *
     * AES 128位密钥（默认）
     *
     * @param key
     * @param algorithm
     * @return
     */
    private static byte[] getInvalidKey(byte[] key, String algorithm) {
        int keySize = 0;
        if (AESAlgorithmType.AES.getName().equals(algorithm)) {
            keySize = 128;// DES算法需要8位密钥
        }
        byte[] invalidKey = new byte[keySize];
        for (int i = 0; i < keySize; i++) {
            if (i < key.length - 1) {
                invalidKey[i] = key[i];
            } else {
                invalidKey[i] = 0;
            }
        }
        return invalidKey;
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
     * 根据算法、工作模式，计算向量，返回空向量（全为0）
     * @param algorithm
     * @param workingMode
     * @param padding
     * @return
     */
    private static byte[] initIv(String algorithm, String workingMode, String padding) {
        int blockSize = getAlgorithmBlockSize(algorithm, workingMode, padding);
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; i++) {
            iv[i] = 0;// 全部为0，即相当于无向量
        }
        return iv;
    }
    // 根据key和算法获取向量（key不足8位时补0）
    private static byte[] initKeyIv(byte[] key, String algorithm, String workingMode, String padding) {
        int blockSize = getAlgorithmBlockSize(algorithm, workingMode, padding);
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; i++) {
            if (i < key.length - 1) {
                iv[i] = key[i];
            } else {
                iv[i] = 0;
            }
        }
        return iv;
    }
    // 获取指定加密算法加密时的加密块大小
    private static int getAlgorithmBlockSize(String algorithm, String workingMode, String padding) {
        String fullAlg = algorithm + "/" + workingMode + "/" + padding;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(fullAlg);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        int blockSize = cipher.getBlockSize();

        return blockSize;
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
