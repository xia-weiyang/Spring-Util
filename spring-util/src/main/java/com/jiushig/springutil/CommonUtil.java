package com.jiushig.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by zk on 2018/8/15.
 */

public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取当前项目运行路径
     *
     * @return
     */
    public static String getSystemPath() {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        int index = path.indexOf("target") - 1;
        return index > 0 ? path.substring(0, index) : System.getProperty("user.dir");
    }

    /**
     * 检查对象是不是为null
     * 如果为字符串 则不能为空字符串
     *
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object... objects) {
        if (objects == null) return true;
        for (Object obj : objects) {
            if (obj == null) return true;
            if (obj instanceof String) {
                if (((String) obj).isEmpty())
                    return true;
            }
        }
        return false;
    }

    /**
     * 通过反射，简单复制对象
     *
     * @param object     被复制的对象
     * @param copyObject 将对象复制到此对象
     */
    public static void copyObject(Object object, Object copyObject) {
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            try {
                copyObject.getClass().getField(field.getName()).set(copyObject, field.get(object));
            } catch (Exception e) {
                logger.warn("The object copy failed : " + object.toString(), e);
            }
        }
    }

    /**
     * 判断是否少于几
     *
     * @param less
     * @param integers
     * @return
     */
    public static boolean isLess(int less, Integer... integers) {
        if (integers == null) return true;
        for (Integer integer : integers) {
            if (integer == null || integer < less)
                return true;
        }
        return false;
    }

    public static boolean isLessZero(Integer... integers) {
        return isLess(0, integers);
    }

    public static boolean isLessZero(Long... longs) {
        return isLess(0, longs);
    }

    /**
     * 判断是否少于几
     *
     * @param less
     * @param longs
     * @return
     */
    public static boolean isLess(long less, Long... longs) {
        if (longs == null) return true;
        for (Long l : longs) {
            if (l == null || l < less)
                return true;
        }
        return false;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (logger.isInfoEnabled()) {
                logger.trace("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    if (logger.isInfoEnabled()) {
                        logger.trace("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    if (logger.isInfoEnabled()) {
                        logger.trace("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (logger.isInfoEnabled()) {
                        logger.trace("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    if (logger.isInfoEnabled()) {
                        logger.trace("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    if (logger.isInfoEnabled()) {
                        logger.trace("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                    }
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (String ip1 : ips) {
                    if (!("unknown".equalsIgnoreCase(ip1))) {
                        ip = ip1;
                        break;
                    }
                }
            }
            return ip;
        } catch (Exception e) {
            logger.warn("Ip information got failed", e.getMessage());
        }
        return null;
    }

    /**
     * 格式化文本
     *
     * @param text
     * @return
     */
    public static String formatText(String text) {
        if (isEmpty(text)) return text;
        // 删除多余换行符
        text = text.replaceAll("(\n){2,}", "\n");

        return text;
    }

    /**
     * 格式化markdow文本
     *
     * @param markdownText
     * @return
     */
    public static String formatMarkdownText(String markdownText) {
        markdownText = formatText(markdownText);
        if (isEmpty(markdownText)) return null;
        // markdown 替换
        markdownText = markdownText.replaceAll("[#*`]", "");
        markdownText = markdownText.replaceAll("!\\[.*?\\]\\(.*?\\)", "[图片]");
        markdownText = markdownText.replaceAll("\\[.*?\\]\\(.*?\\)", "[链接]");
        return markdownText;
    }

    /**
     * 删除换行等
     *
     * @param string
     * @return
     */
    public static String handleString(String string) {
        if (string == null) return null;
        return string.replaceAll("[\r\n]", "");
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static int createCode() {
        int code = (int) (Math.random() * 100000);
        if (code < 10000)
            return createCode();
        return code;
    }

    /**
     * UUID 不包含"-"连接符
     *
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建文件
     *
     * @param pathAndName 文件路径,包含其文件名
     * @return
     */
    public static File createFile(String pathAndName) {
        assert (!isEmpty(pathAndName));
        pathAndName = pathAndName.replaceAll("\\\\", "/");

        if (!pathAndName.contains("/")) throw new RuntimeException("Error param pathAndName.");
        int lastIndexOf = pathAndName.lastIndexOf("/");
        String path = pathAndName.substring(0, lastIndexOf);
        String name = pathAndName.substring(lastIndexOf + 1);
        if (isEmpty(path, name)) throw new RuntimeException("Error path or name.");

        File filePath = new File(path);
        if (!filePath.exists())
            if (!filePath.mkdirs()) throw new RuntimeException("Create dirs fail.");
        try {
            File file = new File(path, name);
            if (!file.exists()) {
                if (!file.createNewFile()) throw new RuntimeException("Create file fail.");
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * b转为kb 并向上取整
     *
     * @param b
     * @return
     */
    public static int bToKb(int b) {
        return (int) Math.ceil(((float) b) / 1024f);
    }


    public static int toInt(Integer integer) {
        return integer == null ? 0 : integer;
    }

    /**
     * 将list转换为分割的的字符串
     *
     * @param list
     * @param split
     * @return
     */
    public static String convertSplitString(@NonNull List<?> list, @NonNull String split) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : list)
            stringBuilder.append(obj.toString()).append(split);
        return stringBuilder.toString();
    }

    /**
     * {@link #convertSplitString}
     * 默认为；分割
     *
     * @param list
     * @return
     */
    public static String convertSplitString(@NonNull List<?> list) {
        return convertSplitString(list, ";");
    }

    /**
     * 将分割的的字符串转换为list
     *
     * @param splitString
     * @param split
     * @return
     */
    public static List<String> convertListByString(@NonNull String splitString, @NonNull String split) {
        if (isEmpty(splitString)) return new ArrayList<>();
        return Arrays.asList(splitString.split(split));
    }


    /**
     * {@link #convertListByString}
     * 默认为；分割
     *
     * @param splitString
     * @return
     */
    public static List<String> convertListByString(@NonNull String splitString) {
        return convertListByString(splitString, ";");
    }

    /**
     * 从某个地址中获取ip
     *
     * @param host
     * @return
     */
    public static String getIpFromHost(String host) {
        if (isEmpty(host)) return null;
        if (host.startsWith("http")) {
            return host.split(":")[1].replaceAll("/", "");
        }
        return host.split(":")[0];
    }

    /**
     * 比较请求头中的版本是否大于[version]
     * 对测试版本号忽略，例如4.9-b 实际比较的大小为4.9
     *
     * @return 大于返回1，等于0，小于-1
     */
    public static int compareVersion(HttpServletRequest request, String version) {
        final String ver = request.getHeader("version");
        if (ver == null || ver.isEmpty()) return -1;
        if (ver.equals(version)) return 0;
        try {
            String[] verSplit = ver.split("-");
            String[] versionSplit = version.split("-");
            final int compareTo = versionSplit[0].compareTo(verSplit[0]);
            if (compareTo > 0) return -1;
            if (compareTo < 0) return 1;
            return 0;
        } catch (Exception e) {
            logger.error(String.format("compareVersion fail: %s  reqVer:%s", version, ver), e);
        }
        return -1;
    }
}
