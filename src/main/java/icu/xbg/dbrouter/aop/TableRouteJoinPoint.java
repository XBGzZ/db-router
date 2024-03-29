
package icu.xbg.dbrouter.aop;

import icu.xbg.dbrouter.annotation.TBRouter;
import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.meta.RouteMetaInfo;
import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.TBStrategy;
import icu.xbg.dbrouter.utils.ArgsUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  表路由注解的切面方法，用来填写表上下文信息
 *
 *  表路由则是在数据库连接上之后才需要获取的
 *  因为其原理是在sql提交之前拦截，所以顺序
 *  上应该尽量靠后，采用默认顺序即可
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 19:13
 */
@Aspect
@Component
public class TableRouteJoinPoint {

    @Autowired
    DBRouterProperties properties;


    @Around("@annotation(tbRouter)")
    public void dataBaseRouteContext(ProceedingJoinPoint joinPoint, TBRouter tbRouter) {

        RouteMeta metaInfo = Optional.ofNullable(RouteContext.getMetaInfo()).orElse(new RouteMetaInfo());
        initTableCount(tbRouter, metaInfo);
        initDataBaseMetaInfo(tbRouter, metaInfo, joinPoint);
        RouteContext.setMetaInfo(metaInfo);

        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            clearContext();
        }
    }
    private void initTableCount(TBRouter tbRouter, RouteMeta routeMeta) {
        int i = tbRouter.tableCount();
        // 超过0 采用注解策略
        if (i<1){
            String dataSourceName = RouteContext.getMetaInfo().getDataSourceName();
            DBProperties dbProperties = null;
            if (StringUtils.hasLength(dataSourceName)){
                Optional<DBProperties> first = properties.getDbPool().stream().filter(item -> item.getDbName().equals(dataSourceName))
                        .findFirst();
               dbProperties  = first.orElse(properties.getPrimaryDb());
            }else {
                dbProperties = properties.getPrimaryDb();
            }
            routeMeta.setTableCount(dbProperties.getTbCount());
        } else {
            routeMeta.setTableCount(i);
        }
    }


    // 初始化meta数据
    private void initDataBaseMetaInfo(TBRouter tbRouter, RouteMeta meta, ProceedingJoinPoint joinPoint){
        // 获取局部配置
        TBStrategy strategy = tbRouter.strategy();
        Class<? extends BaseTableRouteStrategy> strategyClass;
        switch (strategy){
            case AUTO -> {
                strategyClass = tbRouter.customStrategy();
                if (strategyClass.equals(BaseTableRouteStrategy.class)){
                    strategyClass = properties.getCommonConfig().getDefaultTBStrategy();
                }
            }
            case CUSTOM -> {
                strategyClass = tbRouter.customStrategy();
                if (strategyClass.equals(BaseTableRouteStrategy.class)){
                    throw new RuntimeException("Custom policies must provide implementation subclasses");
                }
            }
            default -> {
                strategyClass = strategy.getStrategyClass();
            }
        }
        meta.setTableRouteStrategy(strategyClass);
        meta.setTbKeywords(tbRouter.keywords());
        Map<String,Object> argsMap = ArgsUtil.resolveParam(joinPoint);
        meta.setParam(argsMap);
        meta.setTableName(tbRouter.tableName().split(","));
    }



    private void clearContext() {
        RouteContext.clearTBKey();
        RouteContext.clearDBKey();
    }
}
