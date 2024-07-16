package com.jiushig.springutil;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * Created by zk on 2019/1/30.
 */
public class AESCBC {


    /**
     * 加密算法
     */
    private static final String ENCRY_ALGORITHM = "AES";

    /**
     * 加密算法/加密模式/填充类型
     * 本例采用AES加密，ECB加密模式，PKCS5Padding填充
     */
    private static final String CIPHER_MODE = "AES/CBC/PKCS7Padding";


    /**
     * 设置加密密码处理长度。
     * 不足此长度补0；
     */
    private static final int PWD_SIZE = 16;


    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 密码处理方法
     * 如果加解密出问题，
     * 请先查看本方法，排除密码长度不足补"0",导致密码不一致
     *
     * @param password 待处理的密码
     * @return
     */
    private static byte[] pwdHandler(String password) {
        StringBuilder sb = new StringBuilder(password == null ? "" : password);
        while (sb.length() < PWD_SIZE) {
            sb.append("0");
        }
        if (sb.length() > PWD_SIZE) {
            sb.setLength(PWD_SIZE);
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }


    /**
     * 原始加密
     *
     * @param textBytes 明文字节数组，待加密的字节数组
     * @param pwdBytes  加密密码字节数组
     * @return 返回加密后的密文字节数组，加密错误返回null
     */
    private static byte[] encrypt(byte[] textBytes, byte[] pwdBytes) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE, "BC");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(pwdBytes));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, params);
            return cipher.doFinal(textBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param text 待加密的文本
     * @param pwd
     * @return
     */
    public static String encrypt(String text, String pwd) {
        return BASE64.encrypt(encrypt(text.getBytes(StandardCharsets.UTF_8), pwdHandler(pwd)));
    }

    /**
     * 原始解密
     *
     * @param textBytes 密文字节数组，待解密的字节数组
     * @param pwdBytes  解密密码字节数组
     * @return 返回解密后的明文字节数组，解密错误返回null
     */
    private static byte[] decrypt(byte[] textBytes, byte[] pwdBytes) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE,"BC");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(pwdBytes));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, params);
            return cipher.doFinal(textBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param text 待解密的文本
     * @param pwd
     * @return
     */
    public static String decrypt(String text, String pwd) {
        return new String(decrypt(BASE64.decryptToByte(text), pwdHandler(pwd)), StandardCharsets.UTF_8);
    }

}