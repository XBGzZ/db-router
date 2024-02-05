package icu.xbg.dbrouter.strategy.strategys;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.StrategyCache;

import java.util.List;
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
public abstract class BaseDataBaseRouteStrategy extends BaseRouteStrategy {

    public BaseDataBaseRouteStrategy(DBRouterProperties properties) {
        super(properties);

    }

    public BaseDataBaseRouteStrategy(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.DataBase;
    }

    protected String calculateKey(Stream<String> provider, Map<String, Object> args){
       return calculateDataBaseKey(provider,args, primaryDB,poolDB,poolDBProperties);
    }

    public abstract String calculateDataBaseKey(Stream<String> provider, Map<String, Object> args, DBProperties primaryDB, Map<String,DBProperties> poolDB, List<DBProperties> poolDBList);
}
