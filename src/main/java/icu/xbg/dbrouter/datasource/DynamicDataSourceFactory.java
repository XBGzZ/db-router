package icu.xbg.dbrouter.datasource;

import icu.xbg.dbrouter.datasource.source.BaseDynamicDataSource;
import icu.xbg.dbrouter.meta.MetaResolver;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  动态数据源工厂，只是生产动态数据源
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 15:02
 */
@FunctionalInterface
public interface DynamicDataSourceFactory {
    BaseDynamicDataSource getInstance(MetaResolver resolver);
}
