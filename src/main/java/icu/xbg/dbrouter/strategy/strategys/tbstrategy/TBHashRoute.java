package icu.xbg.dbrouter.strategy.strategys.tbstrategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  简单的Hash路由，针对Table的路由
 *  只能和方法入参进行匹配，只能匹配参数名
 *  不能深度匹配
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 20:34
 */
public class TBHashRoute extends BaseTableRouteStrategy {

    public TBHashRoute(DBRouterProperties properties) {
        super(properties);
    }

    public TBHashRoute(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }



    @Override
    public String calculateDataBaseKey(Stream<String> keywordsProvider, Map<String, Object> args, RouteMeta routeMetaInfo, DBProperties primaryDB, Map<String, DBProperties> poolDB) {
        Object[] objects = keywordsProvider.map(args::get).filter(Objects::nonNull)
                .toArray();
        Integer tableCount = routeMetaInfo.getTableCount();
        int hash = Objects.hash(objects);
        hash = hash ^ hash >>> 16;
        int index = hash % tableCount;
        return String.valueOf(index);
    }
}
