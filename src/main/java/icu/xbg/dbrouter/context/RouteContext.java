package icu.xbg.dbrouter.context;

import icu.xbg.dbrouter.meta.RouteMeta;

import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  上下文环境，持有特殊上下文
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 19:24
 */
public class RouteContext {

    // 解析后的metaInfo参数
    private static final ThreadLocal<RouteMeta> metaInfo = new ThreadLocal<>();
    // 上下文关键词 context 参数,存放解析后最直接的参数信息
    private static final ThreadLocal<String> dbKey = new ThreadLocal<>();

    // 还需要调整
    private static final ThreadLocal<String[]> tbKeys = new ThreadLocal<>();

    private static final ThreadLocal<String> tableSuffix = new ThreadLocal<>();

    public static void setMetaInfo(RouteMeta routeMeta){
        metaInfo.set(routeMeta);
    }
    public static RouteMeta getMetaInfo(){
        return metaInfo.get();
    }

    public static void clearMetaInfo(){
        metaInfo.remove();
    }

    public static void setDBKey(String dbKeyIdx){
        dbKey.set(dbKeyIdx);
    }

    public static String getDBKey(){
        return dbKey.get();
    }

    public static void setTBKeys(String[] tbKeyIdx){
        tbKeys.set(tbKeyIdx);
    }

    public static String getTableSuffix(){
        return tableSuffix.get();
    }

    public static void setTableSuffix(String suffix){
        tableSuffix.set(suffix);
    }

    public static String[] getTBKeys(){
        return tbKeys.get();
    }

    public static void clearDBKey(){
        dbKey.remove();
        if (Objects.isNull(tbKeys.get())){
            clearMetaInfo();
        }
    }

    public static void clearTBKey(){
        tbKeys.remove();
        tableSuffix.remove();
        if (Objects.isNull(dbKey.get())){
            clearMetaInfo();
        }
    }
}
