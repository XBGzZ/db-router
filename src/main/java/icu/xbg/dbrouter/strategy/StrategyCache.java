package icu.xbg.dbrouter.strategy;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 18:09
 */
public interface StrategyCache {
    RouteStrategy getStrategy(Class<? extends RouteStrategy> strategyClass);

    void registerStrategy(RouteStrategy routeStrategy);

    void removeStrategy(RouteStrategy routeStrategy);
}
