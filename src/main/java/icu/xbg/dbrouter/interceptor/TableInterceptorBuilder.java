package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.meta.MetaResolver;

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
 * @date 2024-02-05 14:53
 */
public interface TableInterceptorBuilder {
    public InvocationHandler buildProxy(Connection connection, DBProperties dbProperties);

    public MetaResolver getResolver();
}
