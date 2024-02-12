package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;

import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:28
 */
public abstract class AbstractMetaResolver implements MetaResolver{

    protected StrategyCache cache;



    protected AbstractMetaResolver(StrategyCache cache){
        this.cache = cache;
    }

    public abstract String resolve(String keyword, RouteStrategy strategy, Map<String,Object> args);

    @Override
    public RouteStrategy getStrategy(Class<? extends RouteStrategy> strategyClass) {
        return Objects.isNull(strategyClass)?null:cache.getStrategy(strategyClass);
    }
    @Override
    public void resolveAndRecordTableInfo(RouteMeta meta, Map<String,Object> params) {
        String suffix = resolve(meta.getTbKeywords(), getStrategy(meta.getTableRouteStrategy()), params);
        recordTableSuffix(suffix);
        RouteContext.setTBKeys(meta.getTableName());
        meta.getTableName();
    }

    @Override
    public void resolveAndRecordDataBaseInfo(RouteMeta meta, Map<String,Object> params) {
        String dbName = resolve(meta.getDbKeywords(), getStrategy(meta.getDataBaseRouteStrategy()), params);
        recordDataBaseName(dbName);
    }

    // 记录数据
    protected void recordDataBaseName(String name){
        RouteMeta metaInfo = RouteContext.getMetaInfo();
        RouteContext.setDBKey(name);
        metaInfo.setDataSourceName(name);
    }


    protected void recordTableSuffix(String suffix){
        RouteContext.setTableSuffix(suffix);
    }
}
