package icu.xbg.dbrouter.annotation;


import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.TBStrategy;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     表路由策略
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:32
 */
// TODO 后续将会支持类级别的注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface TBRouter {
    /**
     * 关键词，后续可支持Spel表达式
     * @return
     */
    String keywords() default "";

    /**
     *  表名
     */
    String tableName() default "";

    /**
     *  表路由策略
     *  AUTO: 开启自动方式，默认，以配置文件为主，如果配置文件中
     *      没有配置默认策略，那么不路由，
     *  CUSTOM: CUSTOM策略必须填写 customStrategy指定
     *
     *  其他: 指定对应策略
     * @return
     */
    TBStrategy strategy() default TBStrategy.AUTO;

    /**
     * 表数量，默认为0,如果超过0，以注释优先
     * 如果小于0，按配置文件来，表后缀默认从0开始
     * @return
     */
    int tableCount() default 0;

    /**
     * 提供自定义表路由策略
     * @return
     */
    Class<? extends BaseTableRouteStrategy> customStrategy() default BaseTableRouteStrategy.class;



}
