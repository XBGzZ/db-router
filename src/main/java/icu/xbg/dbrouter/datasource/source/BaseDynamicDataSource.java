package icu.xbg.dbrouter.datasource.source;


import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.interceptor.SimpleTableInterceptorBuilder;
import icu.xbg.dbrouter.interceptor.TableInterceptorBuilder;
import icu.xbg.dbrouter.meta.MetaResolver;

import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.strategy.StrategyCache;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 22:04
 */
public abstract class BaseDynamicDataSource extends AbstractRoutingDataSource {

    protected MetaResolver resolver;

    protected Map<String,Integer> dbTablesCountCache;

    protected DBRouterProperties properties;

    protected TableInterceptorBuilder connectProxyBuilder;


    public BaseDynamicDataSource(TableInterceptorBuilder connectProxyBuilder, DBRouterProperties properties) {
        this(connectProxyBuilder.getResolver(), properties,connectProxyBuilder);
    }

    private BaseDynamicDataSource(MetaResolver resolver, DBRouterProperties properties, TableInterceptorBuilder builder) {
        this.resolver = resolver;
        this.properties = properties;
        Map<String, Integer> map = new HashMap<>();
        properties.getDbPool().forEach(item->{
            map.put(item.getDbName(),item.getTbCount());
        });
        if (Objects.nonNull(properties.getPrimaryDb())){
            map.putIfAbsent(properties.getPrimaryDb().getDbName(), Optional.ofNullable(properties.getPrimaryDb().getTbCount()).orElse(1));
        }
        dbTablesCountCache = map;
        this.connectProxyBuilder = builder;

    }
    public BaseDynamicDataSource(MetaResolver resolver, DBRouterProperties properties, StrategyCache cache) {
        this(resolver,properties,new SimpleTableInterceptorBuilder(resolver,cache));
    }


    protected abstract String getDataBaseKey(RouteMeta metaInfo,String currentDataBaseName);

    // 查询数据库key
    @Override
    public Object determineCurrentLookupKey() {
        parseMetaData();
        return getDataBaseKey(RouteContext.getMetaInfo(),RouteContext.getDBKey());
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        return proxyConnection(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username,password);
        return proxyConnection(connection);
    }

    private Connection proxyConnection(Connection connection) {
        Optional<DBProperties> first = properties.getDbPool().stream().filter(item -> item.getDbName().equals(RouteContext.getDBKey())).findFirst();
        DBProperties dbProperties = first.orElse(properties.getPrimaryDb());
        return (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, connectProxyBuilder.buildProxy(connection,dbProperties));
    }
    // 解析 & 装填 路由key 到上下文中
    protected void parseMetaData() {
        RouteMeta metaInfo = RouteContext.getMetaInfo();
        if (Objects.isNull(metaInfo)){
            throw new RuntimeException("Meta data could not be Null");
        }
        resolver.resolveDataBase(metaInfo,RouteContext.getMetaInfo().getParam());
        metaInfo.setTableCount(dbTablesCountCache.get(RouteContext.getDBKey()));
    }
}
