package icu.xbg.dbrouter.annotation;


import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.BaseTableRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.DBStrategy;
import icu.xbg.dbrouter.strategy.strategys.TBStrategy;

import java.lang.annotation.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     分表策略
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:32
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface TBRouter {
    /**
     * 关键词，后续可支持Spel表达式
     * @return
     */
    String keywords() default "";

    String tableName() default "";

    // 策略
    TBStrategy strategy() default TBStrategy.AUTO;

    int tableCount() default 1;

    boolean complexMode() default false;
    // 如果customStrategy的值不为Empty那么就以custom为准
    Class<? extends BaseTableRouteStrategy> customStrategy() default BaseTableRouteStrategy.class;



}
