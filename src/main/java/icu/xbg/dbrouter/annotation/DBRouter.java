package icu.xbg.dbrouter.annotation;



import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.DBStrategy;

import java.lang.annotation.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     路由策略
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:32
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    /**
     * 关键词，后续可支持Spel表达式
     * @return
     */
    String keywords() default "";

    // 策略
    DBStrategy strategy() default DBStrategy.AUTO;

    // 如果customStrategy的值不为Empty那么就以custom为准
    Class<? extends BaseDataBaseRouteStrategy> customStrategy() default BaseDataBaseRouteStrategy.class;

}
