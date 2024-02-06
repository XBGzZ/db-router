package icu.xbg.dbrouter.strategy;

import icu.xbg.dbrouter.meta.RouteMeta;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     路由策略，输入一个key，返回对应的key
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 17:22
 */
public interface RouteStrategy {
    // 获取key
    String getKey(String[] keywords, Map<String,Object> args);

    String getKey(String keyword,Map<String,Object> args);

    default String getCacheName(){
        return this.getClass().getName();
    };
    StrategyType getStrategyType();
    enum StrategyType{
        Table,DataBase
    }
}
