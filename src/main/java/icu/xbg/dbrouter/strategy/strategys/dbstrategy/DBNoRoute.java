package icu.xbg.dbrouter.strategy.strategys.dbstrategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;

import java.util.List;
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
 * @date 2024-02-06 13:54
 */
public final class DBNoRoute extends BaseDataBaseRouteStrategy {
    public DBNoRoute(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }

    @Override
    public String calculateDataBaseKey(Stream<String> keywordsProvider, Map<String, Object> args, DBProperties primaryDB, Map<String, DBProperties> poolDB, List<DBProperties> poolDBList) {
        return primaryDB.getDbName();
    }
}
