package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-05 14:23
 */
@Getter
@Setter
public class SimpleTableInterceptorBuilder implements TableInterceptorBuilder {

    protected MetaResolver resolver;

    protected StrategyCache cache;
    public SimpleTableInterceptorBuilder(MetaResolver resolver,StrategyCache cache) {
        this.resolver = resolver;
        this.cache = cache;
    }
    public SimpleTableInterceptorBuilder(MetaResolver resolver) {
        this(resolver, DefaultStrategyCache.getInstance());
    }
    public InvocationHandler buildProxy(Connection connection, DBProperties dbProperties){
        return new SimpleTableNameInterceptor(connection,dbProperties,resolver,cache);
    }
}
