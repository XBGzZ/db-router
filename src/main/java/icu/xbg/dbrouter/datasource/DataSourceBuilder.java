package icu.xbg.dbrouter.datasource;

import icu.xbg.dbrouter.config.DBProperties;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  单数据源构造
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 17:56
 */
public interface DataSourceBuilder{
    DataSource buildDataSource(DBProperties properties);

    String getBuilderName();
}
