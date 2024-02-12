package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.strategy.StrategyCache;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *   拦截器构造者
 * </pre>
 *
 * @author XBG
 * @date 2024-02-05 14:23
 */
@Getter
@Setter
public class SimpleTableInterceptorBuilder implements TableInterceptorBuilder {

    protected MetaResolver resolver;

    public SimpleTableInterceptorBuilder(MetaResolver resolver) {
        this.resolver = resolver;
    }

    public InvocationHandler buildProxy(Connection connection, DBProperties dbProperties){
        return new SimpleTableNameInterceptor(connection,dbProperties,resolver);
    }
}
