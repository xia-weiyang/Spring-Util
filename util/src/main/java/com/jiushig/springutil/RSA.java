package com.jiushig.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by xia on 2017/11/21.
 * RSA 加密
 */
public class RSA {

    private static final Logger logger = LoggerFactory.getLogger(RSA.class.getSimpleName());

    public static void init() throws Exception {
        KeyPair keyPair = genKeyPair(1024);

        //获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();

        String publicStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        logger.info("公钥：" + publicStr);

        //获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        String privateStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
        logger.info("私钥：" + privateStr);

        //公钥加密
        String msg = encrypt(publicStr, "RSA");
        logger.info("加密后：" + msg);

        //私钥解密
        logger.info("解密后：" + decrypt(privateStr, msg));
    }

    //生成密钥对
    private static KeyPair genKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    //公钥加密
    private static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    private static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    //将base64编码后的公钥字符串转成PublicKey实例
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //将base64编码后的私钥字符串转成PrivateKey实例
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 通过公钥加密
     *
     * @param publicKey
     * @param msg
     * @return
     * @throws Exception
     */
    public static String encrypt(String publicKey, String msg) throws Exception {
        return encrypt(msg, getPublicKey(publicKey));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param msg
     * @return
     * @throws Exception
     */
    public static String decrypt(String privateKey, String msg) throws Exception {
        return decrypt(msg, getPrivateKey(privateKey));
    }


    //公钥加密，并转换成十六进制字符串打印出来
    private static String encrypt(String content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8 - 11;
        byte[][] arrays = splitBytes(content.getBytes(), splitLength);
        StringBuilder sb = new StringBuilder();
        for (byte[] array : arrays) {
            sb.append(bytesToHexString(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //私钥解密，并转换成十六进制字符串打印出来
    private static String decrypt(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8;
        byte[] contentBytes = hexString2Bytes(content);
        byte[][] arrays = splitBytes(contentBytes, splitLength);
        StringBuilder sb = new StringBuilder();
        for (byte[] array : arrays) {
            sb.append(new String(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //拆分byte数组
    private static byte[][] splitBytes(byte[] bytes, int splitLength) {
        int x; //商，数据拆分的组数，余数不为0时+1
        int y; //余数
        y = bytes.length % splitLength;
        if (y != 0) {
            x = bytes.length / splitLength + 1;
        } else {
            x = bytes.length / splitLength;
        }
        byte[][] arrays = new byte[x][];
        byte[] array;
        for (int i = 0; i < x; i++) {

            if (i == x - 1 && bytes.length % splitLength != 0) {
                array = new byte[bytes.length % splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, bytes.length % splitLength);
            } else {
                array = new byte[splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, splitLength);
            }
            arrays[i] = array;
        }
        return arrays;
    }

    //byte数组转十六进制字符串
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //十六进制字符串转byte数组
    private static byte[] hexString2Bytes(String hex) {
        int len = (hex.length() / 2);
        hex = hex.toUpperCase();
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
