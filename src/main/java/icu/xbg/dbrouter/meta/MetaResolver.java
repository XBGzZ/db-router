package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.strategy.RouteStrategy;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  解析器
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:23
 */
public interface MetaResolver {
    /**
     * DataSource信息的解析，并保存到RouteContext
     * @param meta
     * @param args
     */
    public void resolveAndRecordDataBaseInfo(RouteMeta meta, Map<String,Object> args);

    /**
     * Table信息的解析，并保存到RouteContext
     * @param meta
     * @param args
     */
    public void resolveAndRecordTableInfo(RouteMeta meta, Map<String,Object> args);

    /**
     * 获取策略
     * @param strategyClass
     * @return
     */
    public RouteStrategy getStrategy(Class<? extends RouteStrategy> strategyClass);
}
