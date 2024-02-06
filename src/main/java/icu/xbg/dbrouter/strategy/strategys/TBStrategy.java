package icu.xbg.dbrouter.strategy.strategys;

import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.tbstrategy.TBHashRoute;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 19:33
 */
public enum TBStrategy {
    AUTO(null),CUSTOM(null),HASH(TBHashRoute.class)
    ;
    private final Class<? extends BaseTableRouteStrategy> strategyClass;
    TBStrategy(Class<? extends BaseTableRouteStrategy> strategyClass){
        this.strategyClass = strategyClass;
    }

    public Class<? extends BaseTableRouteStrategy> getStrategyClass(){
        return strategyClass;
    }
}
