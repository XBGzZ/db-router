package icu.xbg.dbrouter.strategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.meta.RouteMeta;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  数据库路由策略
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 17:23
 */
public abstract class BaseTableRouteStrategy extends BaseRouteStrategy implements RouteStrategy {

    public BaseTableRouteStrategy(DBRouterProperties properties) {
        super(properties);
    }

    public BaseTableRouteStrategy(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.Table;
    }

    @Override
    public String calculateKey(Stream<String> keyword, Map<String, Object> args) {
        return calculateDataBaseKey(keyword,args,RouteContext.getMetaInfo(),primaryDB,poolDB);
    }

    public abstract String calculateDataBaseKey(Stream<String> keywordsProvider, Map<String, Object> args, RouteMeta routeMetaInfo, DBProperties primaryDB, Map<String,DBProperties> poolDB);

}

