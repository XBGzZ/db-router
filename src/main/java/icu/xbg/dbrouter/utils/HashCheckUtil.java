package icu.xbg.dbrouter.utils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 17:10
 */
public class HashCheckUtil {
    public static boolean hasHashCodeOverride(Class<?> clazz) {
        try {
            Method hashCodeMethod = clazz.getMethod("hashCode");
            // 如果hashCode方法是Object类中定义的，则没有被重写
            return !Objects.equals(hashCodeMethod.getDeclaringClass(), Object.class);
        } catch (NoSuchMethodException e) {
            return false; // 没有找到hashCode方法
        }
    }
}
