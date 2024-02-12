package icu.xbg.dbrouter.datasource.source;

import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.interceptor.SimpleTableInterceptorBuilder;
import icu.xbg.dbrouter.interceptor.TableInterceptorBuilder;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.strategy.StrategyCache;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *      DynamicDataSource 只能完成数据库的拦截
 *      但是无法实现表的拦截
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:43
 */
public class DefaultDynamicDataSource extends BaseDynamicDataSource {

    public DefaultDynamicDataSource(MetaResolver resolver, DBRouterProperties properties, TableInterceptorBuilder builder) {
        super(resolver, properties, builder);
    }

    @Override
    protected String getDataBaseKey(RouteMeta metaInfo, String currentDataBaseName) {
        return currentDataBaseName;
    }


}
