package icu.xbg.dbrouter.datasource;

import icu.xbg.dbrouter.datasource.source.BaseDynamicDataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  动态数据源构造
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:08
 */
public interface DynamicDataSourceBuilder {
    BaseDynamicDataSource build();

}
