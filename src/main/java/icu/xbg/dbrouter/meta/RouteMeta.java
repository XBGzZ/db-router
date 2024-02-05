package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.strategy.strategys.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.BaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.BaseTableRouteStrategy;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 17:25
 */
public interface RouteMeta {
    // 库关键词
    String getDbKeywords();

    void setDbKeywords(String keywords);

    // 表名关键词
    String getTbKeywords();
    void setTbKeywords(String keywords);

    // 策略类
    Class<? extends BaseTableRouteStrategy> getTableRouteStrategy();
    void setTableRouteStrategy(Class<? extends BaseTableRouteStrategy>  baseTableRouteStrategy);

    // 数据库策略类
    Class<? extends BaseDataBaseRouteStrategy> getDataBaseRouteStrategy();
    void setDataBaseRouteStrategy(Class<? extends BaseDataBaseRouteStrategy> baseTableRouteStrategy);

    void setTableCount(Integer count);

    Integer getTableCount();

    String getDataSourceName();

    void setDataSourceName(String dataSourceName);

    public Map<String,Object> getParam();

    public void setParam(Map<String,Object> param);

    void setTableName(String[] tableNames);

    String[] getTableName();
}
