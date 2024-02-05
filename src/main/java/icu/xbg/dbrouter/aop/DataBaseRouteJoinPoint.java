package icu.xbg.dbrouter.aop;

import icu.xbg.dbrouter.annotation.DBRouter;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.meta.RouteMetaInfo;
import icu.xbg.dbrouter.strategy.strategys.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.DBStrategy;
import icu.xbg.dbrouter.utils.ArgsUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *   需要注意事务的执行顺序
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 19:13
 */
@Aspect
@Component
@Order(value = -4399)
public class DataBaseRouteJoinPoint {

    @Autowired
    DBRouterProperties properties;


    @Around("@annotation(dbRouter)")
    public Object dataBaseRouteContext(ProceedingJoinPoint joinPoint, DBRouter dbRouter) throws Throwable {
        RouteMeta metaInfo = Optional.ofNullable(RouteContext.getMetaInfo()).orElse(new RouteMetaInfo());
        initDataBaseMetaInfo(dbRouter, metaInfo, joinPoint);
        RouteContext.setMetaInfo(metaInfo);

        try {
            return joinPoint.proceed();
        } finally {
            clearContext();
        }
    }
    // 初始化meta数据
    private void initDataBaseMetaInfo(DBRouter dbRouter, RouteMeta meta, ProceedingJoinPoint joinPoint){
        // 获取局部配置
        DBStrategy strategy = dbRouter.strategy();
        Class<? extends BaseDataBaseRouteStrategy> strategyClass;
        switch (strategy){
            // AUTO 以配置文件未准
            case AUTO -> {
                strategyClass = dbRouter.customStrategy();
                if (strategyClass.equals(BaseDataBaseRouteStrategy.class)){
                    strategyClass = properties.getCommonConfig().getDefaultDBStrategy();
                }
            }
            // 用户必须临时指定
            case CUSTOM -> {
                strategyClass = dbRouter.customStrategy();
                if (strategyClass.equals(BaseDataBaseRouteStrategy.class)){
                    throw new RuntimeException("Custom policies must provide implementation subclasses");
                }
            }
            default -> {
                strategyClass = strategy.getStrategyClass();
            }
        }
        meta.setDataBaseRouteStrategy(strategyClass);
        meta.setDbKeywords(dbRouter.keywords());
        Map<String,Object> argsMap = ArgsUtil.resolveParam(joinPoint);
        meta.setParam(argsMap);
    }

    private void clearContext() {
        RouteContext.clearDBKey();
    }
}
