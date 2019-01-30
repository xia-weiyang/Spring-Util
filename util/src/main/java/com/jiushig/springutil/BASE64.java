package com.jiushig.springutil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by zk on 2019/1/30.
 */
public class BASE64 {
    /**
     * BASE64解密
     *
     * @param text
     * @return
     */
    public static String decrypt(String text) {
        return new String(decryptToByte(text), StandardCharsets.UTF_8);
    }

    public static byte[] decryptToByte(String text) {
        try {
            return (new BASE64Decoder()).decodeBuffer(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * BASE64加密
     *
     * @param text
     * @return
     */
    public static String encrypt(String text) {
        return encrypt(text.getBytes());
    }


    public static String encrypt(byte[] bytes) {
        return (new BASE64Encoder()).encodeBuffer(bytes);
    }

}
