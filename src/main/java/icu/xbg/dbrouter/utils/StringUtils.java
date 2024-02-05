package icu.xbg.dbrouter.utils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-05 12:54
 */
public class StringUtils {

    public static boolean allHasLength(String... args){
        for (String str:args){
            if (!hasLength(str)){
                return false;
            }
        }
        return true;
    }

    public static boolean anyHasLength(String... args){
        for (String str:args){
            if (hasLength(str)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(String str){
        return (str != null && !str.isEmpty());
    }
}
