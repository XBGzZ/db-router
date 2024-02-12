package icu.xbg.dbrouter.aop;

import icu.xbg.dbrouter.annotation.DBRouter;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.context.RouteContext;
import icu.xbg.dbrouter.meta.RouteMeta;
import icu.xbg.dbrouter.meta.RouteMetaInfo;
import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;
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
 *   数据库路由的切面处理方法，用来获取上下文信息
 *
 *   注意点：由于声明式事务会利用切面来讲方法包裹
 *   使用拦截链的方式来处理，但是不管如何其本身需
 *   要获取数据库连接。
 *   因此会在事务切面中获取数据库连接（getConnection）
 *   导致时机比原本提前，所以需要DataBaseRouteJoinPoint
 *   的执行时机在事务切面之前
 *
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
                if (strategyClass.isAssignableFrom(BaseDataBaseRouteStrategy.class)){
                    throw new RuntimeException("Custom policies must provide implementation customStrategyClass");
                }
            }
            default ->
                strategyClass = strategy.getStrategyClass();

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
