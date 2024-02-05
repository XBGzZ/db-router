package icu.xbg.dbrouter.strategy.cache;

import icu.xbg.dbrouter.strategy.StrategyCache;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 18:26
 */
public class DefaultStrategyCache extends BaseStrategyCache{

    private static StrategyCache strategyCache;

    private DefaultStrategyCache(){}

    public static StrategyCache getInstance(){
        if (Objects.isNull(strategyCache)){
            synchronized (BaseStrategyCache.class){
                if (Objects.isNull(strategyCache)){
                    strategyCache = new DefaultStrategyCache();
                }
            }
        }
        return strategyCache;
    }
}
