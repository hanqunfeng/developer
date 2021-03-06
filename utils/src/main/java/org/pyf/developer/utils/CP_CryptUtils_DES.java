package org.pyf.developer.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * DES加密和解密。
 */
public class CP_CryptUtils_DES {
    static Logger logger = LoggerFactory.getLogger(CP_CryptUtils.class);

    /**
     * 功能：加密 (UTF-8)
     *
     * @param source  源字符串
     * @return String
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String encrypt(String source, String key) throws UnsupportedEncodingException {
        return encrypt(source, key, "UTF-8");
    }

    /**
     * 功能：解密 (UTF-8)
     *
     * @param encryptedData 被加密后的字符串
     * @return String
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String decrypt(String encryptedData, String key)
            throws UnsupportedEncodingException {
        return decrypt(encryptedData, key, "UTF-8");
    }

    /**
     * 功能：加密
     *
     * @param source  源字符串
     * @param charSet 编码
     * @return String
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String encrypt(String source, String key, String charSet)
            throws UnsupportedEncodingException {
        String encrypt = null;
        byte[] ret = encrypt(source.getBytes(charSet), key);
        encrypt = new String(CP_CryptUtils_Base64.encode(ret));
        return encrypt;
    }

    /**
     * 功能：解密
     *
     * @param encryptedData 被加密后的字符串
     * @param charSet       编码
     * @return String
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String decrypt(String encryptedData, String key, String charSet)
            throws UnsupportedEncodingException {
        String descryptedData = null;
        byte[] ret = descrypt(CP_CryptUtils_Base64.decode(encryptedData.toCharArray()), key);
        descryptedData = new String(ret, charSet);
        return descryptedData;
    }

    /**
     * 加密数据 用生成的密钥加密原始数据
     *
     * @param primaryData 原始数据
     * @return byte[]
     */
    private static byte[] encrypt(byte[] primaryData, String keyData) {

        /** 取得安全密钥 */
        byte rawKeyData[] = getKey();

        /** DES算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();

        /** 使用原始密钥数据创建DESKeySpec对象 */
        DESKeySpec dks = null;
        try {
            dks = new DESKeySpec(keyData.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        /** 创建一个密钥工厂 */
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }

        /** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
        SecretKey key = null;
        try {
            key = keyFactory.generateSecret(dks);
        } catch (InvalidKeySpecException e) {
            logger.error("", e);
        }

        /** Cipher对象实际完成加密操作 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (NoSuchPaddingException e) {
            logger.error("", e);
        }

        /** 用密钥初始化Cipher对象 */
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        } catch (InvalidKeyException e) {
            logger.error("", e);
        }

        /** 正式执行加密操作 */
        byte encryptedData[] = null;
        try {
            encryptedData = cipher.doFinal(primaryData);
        } catch (IllegalStateException e) {
            logger.error("", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("", e);
        } catch (BadPaddingException e) {
            logger.error("", e);
        }

        /** 返回加密数据 */
        return encryptedData;
    }

    /**
     * 用密钥解密数据
     *
     * @param encryptedData 加密后的数据
     * @return byte[]
     */
    private static byte[] descrypt(byte[] encryptedData, String keyData) {

        /** DES算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();

        /** 取得安全密钥 */
        byte rawKeyData[] = getKey();

        /** 使用原始密钥数据创建DESKeySpec对象 */
        DESKeySpec dks = null;
        try {
            dks = new DESKeySpec(keyData.getBytes());
        } catch (InvalidKeyException e) {
            logger.error("", e);
        }

        /** 创建一个密钥工厂 */
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }

        /** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
        SecretKey key = null;
        try {
            key = keyFactory.generateSecret(dks);
        } catch (InvalidKeySpecException e) {
            logger.error("", e);
        }

        /** Cipher对象实际完成加密操作 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (NoSuchPaddingException e) {
            logger.error("", e);
        }

        /** 用密钥初始化Cipher对象 */
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
        } catch (InvalidKeyException e) {
            logger.error("", e);
        }

        /** 正式执行解密操作 */
        byte decryptedData[] = null;
        try {
            decryptedData = cipher.doFinal(encryptedData);
        } catch (IllegalStateException e) {
            logger.error("", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("", e);
        } catch (BadPaddingException e) {
            logger.error("", e);
        }

        return decryptedData;
    }

    /**
     * 取得安全密钥 此方法作废,因为每次key生成都不一样导致解密加密用的密钥都不一样， 从而导致Given final block not
     * properly padded错误.
     *
     * @return byte数组
     */
    private static byte[] getKey() {

        /** DES算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();

        /** 为我们选择的DES算法生成一个密钥生成器对象 */
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
        kg.init(sr);

        /** 生成密钥工具类 */
        SecretKey key = kg.generateKey();

        /** 生成密钥byte数组 */
        byte rawKeyData[] = key.getEncoded();

        return rawKeyData;
    }

    //public static void main(String[] args) {
    //    try {
    //        String aa = UUID.randomUUID().toString();
    //        String s = CP_CryptUtils_DES.encrypt("sunchengqi@nq.com", aa);
    //        System.out.println(s);
    //        System.out.println(CP_CryptUtils_DES.decrypt(s, aa));
    //    } catch (UnsupportedEncodingException e) {
    //        // TODO Auto-generated catch block
    //        e.printStackTrace();
    //    }
    //}

}