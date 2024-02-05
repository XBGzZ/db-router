package icu.xbg.dbrouter.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-05 13:06
 */
public class BeanValidateUtils {

    public static boolean onlySpecifiedFileHasValue(Object bean, String fieldName) throws IllegalAccessException {
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(fieldName)) {
                if (field.get(bean) == null) {
                    return false; // Special field is null
                } else if (field.getType() == String.class && ((String) field.get(bean)).isEmpty()) {
                    return false; // Special field is an empty string
                }
            } else if (field.get(bean) != null && !field.get(bean).toString().isEmpty()) {
                return false; // Other fields are not empty
            }
        }
        return true;
    }

    public static boolean onlySpecifiedFileHasValue(Object bean, String[] collectionFields) throws IllegalAccessException {
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (Arrays.asList(collectionFields).contains(field.getName()) && field.get(bean) instanceof Collection) {
                Collection<?> collection = (Collection<?>) field.get(bean);
                for (Object item : collection) {
                    if (item == null || (item instanceof String && ((String) item).isEmpty())) {
                        return false; // Collection field contains null or empty value
                    }
                }
            }
        }
        return true;
    }


}
