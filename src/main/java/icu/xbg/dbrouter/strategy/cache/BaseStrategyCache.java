package icu.xbg.dbrouter.strategy.cache;

import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 17:44
 */
public abstract class BaseStrategyCache implements StrategyCache {

    // 表路由缓存
    private  Map<Class<? extends RouteStrategy>,RouteStrategy> strategyInstanceCache;


    protected BaseStrategyCache(){
        init();
    }


    protected void init(){
        this.strategyInstanceCache = new ConcurrentHashMap<>();
    }

    @Override
    public RouteStrategy getStrategy(Class<? extends RouteStrategy> strategyClass) {
        return strategyInstanceCache.get(strategyClass);
    }


    @Override
    public void registerStrategy(RouteStrategy routeStrategy) {
        strategyInstanceCache.putIfAbsent(routeStrategy.getClass(),routeStrategy);
    }

    @Override
    public void removeStrategy(RouteStrategy routeStrategy) {
        strategyInstanceCache.remove(routeStrategy.getClass());
    }
}
