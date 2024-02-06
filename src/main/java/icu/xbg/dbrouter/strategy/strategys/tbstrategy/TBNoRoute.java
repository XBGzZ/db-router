package icu.xbg.dbrouter.strategy.strategys.tbstrategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *   无路由
 * </pre>
 *
 * @author XBG
 * @date 2024-02-06 13:56
 */
public final class TBNoRoute extends BaseTableRouteStrategy {
    public TBNoRoute(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }

    @Override
    public String calculateDataBaseKey(Stream<String> keywordsProvider, Map<String, Object> args, RouteMeta routeMetaInfo, DBProperties primaryDB, Map<String, DBProperties> poolDB) {
        return "";
    }
}
