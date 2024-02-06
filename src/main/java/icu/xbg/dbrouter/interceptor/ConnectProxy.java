package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.meta.MetaResolver;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 12:36
 */
@Getter
@Setter
public abstract class ConnectProxy implements InvocationHandler {

    private Connection realConnection;

    private DBProperties properties;

    private MetaResolver resolver;
    private static final String TARGET_NAME = "prepareStatement";

    public ConnectProxy(Connection connection, DBProperties properties,MetaResolver resolver) {
        realConnection = connection;
        this.properties = properties;
        this.resolver = resolver;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals(TARGET_NAME)){
            String modifiedSql = modifyTableName(args);
            return method.invoke(realConnection,modifiedSql);
        }
        // 直接执行
        return method.invoke(realConnection,args);
    }

    private String modifyTableName(Object[] args){
        resolver.resolveAndRecordTable(RouteContext.getMetaInfo(),RouteContext.getMetaInfo().getParam());
        String sql = args[0].toString();
        String[] tbKeys = RouteContext.getTBKeys();
        String dbKey = RouteContext.getDBKey();
        Integer tbCount = Optional.ofNullable(RouteContext.getMetaInfo().getTableCount()).orElse(1);
        return modifyTableName(sql,tbCount,dbKey,tbKeys,RouteContext.getTableSuffix());
    }

    // 动态拦截sql
    protected abstract String modifyTableName(String sql,Integer tbCount,String dbName ,String[] tableNames,String suffix);


}
