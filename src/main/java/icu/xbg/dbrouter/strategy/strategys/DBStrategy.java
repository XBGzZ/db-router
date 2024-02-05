package icu.xbg.dbrouter.strategy.strategys;

import icu.xbg.dbrouter.strategy.strategys.dbstrategy.DBHashRoute;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
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
