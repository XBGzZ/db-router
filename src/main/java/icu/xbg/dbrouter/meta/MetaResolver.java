package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.strategy.RouteStrategy;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:23
 */
public interface MetaResolver {
    public String resolveDataBase(RouteMeta meta, Map<String,Object> args);

    public String[] resolveAndRecordTable(RouteMeta meta, Map<String,Object> args);

    public RouteStrategy getStrategy(Class<? extends RouteStrategy> strategyClass);
}
