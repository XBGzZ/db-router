package icu.xbg.dbrouter.config;

import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.dbstrategy.DBHashRoute;
import icu.xbg.dbrouter.strategy.strategys.tbstrategy.TBHashRoute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 15:32
 */
@Getter
@Setter
public class DBCommonConfig {
    String password;
    String user;
    String driverClassName;
    // 默认策略 auto、random、round、hash
    private Class<? extends BaseDataBaseRouteStrategy> defaultDBStrategy = DBHashRoute.class;
    // 表路由策略
    private Class<? extends BaseTableRouteStrategy> defaultTBStrategy = TBHashRoute.class;
}
