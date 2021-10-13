package com.jiushig.springutil;

import org.apache.commons.codec.binary.Base64;
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
            return Base64.decodeBase64(text);
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
        return Base64.encodeBase64String(bytes);
    }

}
