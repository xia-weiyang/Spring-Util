package com.jiushig.springutil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Utils {

    private Md5Utils() {

    }

    //MD5加密对象
    private static final MessageDigest MD5;

    //初始对象
    static {
        try {
            MD5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Description: MD5加密
     * 如果为0不在进行进制转换
     */
    public static String encrypt(String str) {
        if (str == null || str.isEmpty()) {
            throw new NullPointerException("加密字符不能为空");
        }
        //加密
        byte[] bytes = MD5.digest(str.getBytes());
        return bytesToHexString(bytes);
    }


    //byte数组转十六进制字符串
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        String sTemp;
        for (byte aByte : bytes) {
            sTemp = Integer.toHexString(0xFF & aByte);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}