package com.jiushig.springutil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by zk on 2019/1/30.
 */
public class BASE64 {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     */
    public static String decrypt(String key) {
        try {
            return new String((new BASE64Decoder()).decodeBuffer(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     */
    public static String encrypt(String key) {
        return (new BASE64Encoder()).encodeBuffer(key.getBytes());
    }

}
