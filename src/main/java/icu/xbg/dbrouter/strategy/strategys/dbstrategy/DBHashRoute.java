package icu.xbg.dbrouter.strategy.strategys.dbstrategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  简单的Hash路由，针对数据库的路由
 *  只能和方法入参进行匹配，只能匹配参数名
 *  不能深度匹配
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 20:33
 */
public class DBHashRoute extends BaseDataBaseRouteStrategy {

    public DBHashRoute(DBRouterProperties properties) {
        super(properties);
    }

    public DBHashRoute(StrategyCache cache, DBRouterProperties properties) {
        super(cache, properties);
    }

    @Override
    public String calculateDataBaseKey(Stream<String> keywordsProvider,
                                       Map<String, Object> args,
                                       DBProperties primaryDB,
                                       Map<String, DBProperties> poolDB,
                                       List<DBProperties> poolDBList) {
        Object[] objects = keywordsProvider.map(args::get).filter(Objects::nonNull)
                .toArray();
        if (objects.length==0){
            return primaryDB.getDbName();
        }
        int hash = Objects.hash(objects);
        hash = hash ^ hash >>> 16;
        int index = hash % poolDB.size();
        return poolDBList.get(index).getDbName();
    }


}
