package icu.xbg.dbrouter.strategy.strategys;

import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.dbstrategy.DBHashRoute;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  策略枚举，提供简便的写法
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 19:32
 */
public enum DBStrategy {
    AUTO(null),CUSTOM(null),HASH(DBHashRoute.class)
    ;
    private final Class<? extends BaseDataBaseRouteStrategy> strategyClass;
    DBStrategy(Class<? extends BaseDataBaseRouteStrategy> strategyClass){
        this.strategyClass = strategyClass;
    }

    public Class<? extends BaseDataBaseRouteStrategy> getStrategyClass(){
        return strategyClass;
    }
}
