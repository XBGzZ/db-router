package icu.xbg.dbrouter.datasource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  数据源构造器管理者
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 18:48
 */
public interface DataSourceBuilderManager {
    void registerBuilder(DataSourceBuilder dataSourceBuilder);

    DataSourceBuilder getBuilder(String name);

    DataSourceBuilder getBuilder(Class<? extends DataSourceBuilder> type);

    DataSourceBuilder remove(String name);

    DataSourceBuilder remove(Class<? extends DataSourceBuilder> type);
}
