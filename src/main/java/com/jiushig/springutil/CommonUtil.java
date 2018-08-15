package com.jiushig.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;

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
                logger.warn("对象拷贝失败 " + object.toString(), e);
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
}
