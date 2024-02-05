package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;

import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  Meta数据解析器
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:24
 */
public class DefaultMetaResolver extends AbstractMetaResolver {

    public DefaultMetaResolver(){
        super(DefaultStrategyCache.getInstance());
    }

    public DefaultMetaResolver(StrategyCache cache) {
        super(cache);
    }

    @Override
    public String resolve(String keyword, RouteStrategy strategy, Map<String, Object> args) {
        String key = "";
        if (Objects.nonNull(keyword)){
            String[] keywords = keyword.split(",");
            key = strategy.getKey(keywords,args);
        }
        return key;
    }



}
